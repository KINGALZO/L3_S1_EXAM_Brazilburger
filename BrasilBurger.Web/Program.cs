using BrasilBurger.Web.Data;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

// Configuration Render
var port = Environment.GetEnvironmentVariable("PORT") ?? "10000";
builder.WebHost.UseUrls($"http://*:{port}");

// Services
builder.Services.AddControllersWithViews();

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
                    context.Clients.Add(new BrasilBurger.Web.Models.Client
                    {
                        Email = clientEmail,
                        Password = "alzoking",
                        FirstName = "Alamin",
                        LastName = "Fall",
                        Phone = "+221331234567",
                        Address = "Dakar, Sénégal",
                        IsActive = true
                    });

                    await context.SaveChangesAsync();
                    Console.WriteLine($"✅ Client seed ajouté: {clientEmail}");
                }
                else
                {
                    Console.WriteLine($"ℹ️ Client déjà présent: {clientEmail}");
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