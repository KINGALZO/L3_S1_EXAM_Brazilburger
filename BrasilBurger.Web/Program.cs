using BrasilBurger.Web.Data;
using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Identity;
using BrasilBurger.Web.Models;
using Microsoft.AspNetCore.Mvc.Authorization;

var builder = WebApplication.CreateBuilder(args);

// Configuration Render
var port = Environment.GetEnvironmentVariable("PORT") ?? "10000";
builder.WebHost.UseUrls($"http://*:{port}");

// Services
builder.Services.AddAuthentication(CookieAuthenticationDefaults.AuthenticationScheme)
    .AddCookie(options => {
        options.LoginPath = "/Account/Login";
    });

builder.Services.AddControllersWithViews(options =>
{
    // Require authenticated user by default
    options.Filters.Add(new AuthorizeFilter());
});

// Database configuration (Neon PostgreSQL)
var connectionString = builder.Configuration.GetConnectionString("NeonConnection")
                     ?? Environment.GetEnvironmentVariable("ConnectionStrings__NeonConnection");

if (string.IsNullOrEmpty(connectionString))
{
    connectionString = "Host=localhost;Database=brasilburgerdb;Username=postgres;Password=postgres123;";
}

builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseNpgsql(connectionString));

// Session
builder.Services.AddDistributedMemoryCache();
builder.Services.AddSession();

var app = builder.Build();

// Pipeline
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    app.UseHsts();
}

app.UseStaticFiles();
app.UseRouting();

// Auth
app.UseAuthentication();
app.UseAuthorization();

app.UseSession();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Home}/{action=Index}/{id?}");

// Health check
app.MapGet("/health", () => Results.Ok(new { 
    status = "healthy", 
    timestamp = DateTime.UtcNow,
    database = "Neon PostgreSQL"
}));

// Initialize database
using (var scope = app.Services.CreateScope())
{
    var services = scope.ServiceProvider;
    try
    {
        var context = services.GetRequiredService<ApplicationDbContext>();
        var canConnect = await context.Database.CanConnectAsync();
        
        if (canConnect)
        {
            Console.WriteLine("✅ Connexion à Neon PostgreSQL réussie!");
            await context.Database.EnsureCreatedAsync();

            // Seed default client (email corrected)
            var clientEmail = "alaminfall1@gmail.com";
            try
            {
                if (!await context.Clients.AnyAsync(c => c.Email == clientEmail))
                {
                    var seedClient = new BrasilBurger.Web.Models.Client
                    {
                        Email = clientEmail,
                        Password = null,
                        FirstName = "Alamin",
                        LastName = "Fall",
                        Phone = "+221331234567",
                        Address = "Dakar, Sénégal",
                        IsActive = true
                    };

                    // Hash seed password
                    var hasher = new PasswordHasher<User>();
                    seedClient.PasswordHash = hasher.HashPassword(seedClient, "alzoking");

                    context.Clients.Add(seedClient);

                    await context.SaveChangesAsync();
                    Console.WriteLine($"✅ Client seed ajouté: {clientEmail}");
                }
                else
                {
                    Console.WriteLine($"ℹ️ Client déjà présent: {clientEmail}");
                }

                // Seed sample products (burgers + menus)
                try
                {
                    if (!await context.Burgers.AnyAsync())
                    {
                        var b1 = new BrasilBurger.Web.Models.Burger { Name = "Classic Beef", Description = "Bœuf, fromage, salade, sauce maison", Price = 3500, ImageUrl = "/images/classic-beef.jpg", IsPopular=true };
                        var b2 = new BrasilBurger.Web.Models.Burger { Name = "Chicken Crispy", Description = "Poulet croustillant, salade, sauce piquante", Price = 3200, ImageUrl = "/images/chicken-crispy.jpg" };
                        var b3 = new BrasilBurger.Web.Models.Burger { Name = "Veggie Deluxe", Description = "Galette végétale, avocat, salade", Price = 3000, ImageUrl = "/images/veggie-deluxe.jpg" };
                        context.Burgers.AddRange(b1, b2, b3);
                        await context.SaveChangesAsync();

                        var m1 = new BrasilBurger.Web.Models.Menu { Name = "Menu Classic", Description = "Classic + frites + boisson", Price = b1.Price + 800, BurgerId = b1.Id, ImageUrl = "/images/menu-classic.jpg" };
                        var m2 = new BrasilBurger.Web.Models.Menu { Name = "Menu Crispy", Description = "Chicken Crispy + frites + boisson", Price = b2.Price + 800, BurgerId = b2.Id, ImageUrl = "/images/menu-crispy.jpg" };
                        context.Menus.AddRange(m1, m2);
                        await context.SaveChangesAsync();

                        Console.WriteLine("✅ Seed produits ajouté: Burgers & Menus");
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"⚠️ Erreur lors du seed produits: {ex.Message}");
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"⚠️ Erreur lors du seed: {ex.Message}");
            }
        }
    }
    catch (Exception ex)
    {
        Console.WriteLine($"❌ Erreur DB: {ex.Message}");
    }
}

app.Run();