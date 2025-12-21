using Microsoft.EntityFrameworkCore;
using BrasilBurger.Web.Models;

namespace BrasilBurger.Web.Data
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }
        
        public DbSet<Client> Clients { get; set; }
        public DbSet<Manager> Managers { get; set; }
        public DbSet<Burger> Burgers { get; set; }
        public DbSet<Menu> Menus { get; set; }
        public DbSet<Supplement> Supplements { get; set; }
        public DbSet<Order> Orders { get; set; }
        public DbSet<OrderItem> OrderItems { get; set; }
        public DbSet<Payment> Payments { get; set; }
        
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
            
            modelBuilder.Entity<Client>().ToTable("Clients");
            modelBuilder.Entity<Manager>().ToTable("Managers");
            modelBuilder.Entity<Burger>().ToTable("Burgers");
            modelBuilder.Entity<Menu>().ToTable("Menus");
            modelBuilder.Entity<Supplement>().ToTable("Supplements");
            modelBuilder.Entity<Order>().ToTable("Orders");
            modelBuilder.Entity<OrderItem>().ToTable("OrderItems");
            modelBuilder.Entity<Payment>().ToTable("Payments");
            
            foreach (var entityType in modelBuilder.Model.GetEntityTypes())
            {
                foreach (var property in entityType.GetProperties())
                {
                    if (property.ClrType == typeof(decimal) || property.ClrType == typeof(decimal?))
                    {
                        property.SetColumnType("numeric(10,2)");
                    }
                }
            }
            
            modelBuilder.Entity<Order>()
                .HasOne(o => o.Client)
                .WithMany(c => c.Orders)
                .HasForeignKey(o => o.ClientId)
                .OnDelete(DeleteBehavior.Restrict);
            
            modelBuilder.Entity<OrderItem>()
                .HasOne(oi => oi.Order)
                .WithMany(o => o.OrderItems)
                .HasForeignKey(oi => oi.OrderId);
            
            modelBuilder.Entity<Payment>()
                .HasOne(p => p.Order)
                .WithOne(o => o.Payment)
                .HasForeignKey<Payment>(p => p.OrderId);
            
            modelBuilder.Entity<Menu>()
                .HasOne(m => m.Burger)
                .WithMany()
                .HasForeignKey(m => m.BurgerId);
            
            modelBuilder.Entity<Client>()
                .HasIndex(c => c.Email)
                .IsUnique();
                
            modelBuilder.Entity<Order>()
                .HasIndex(o => o.OrderDate);
                
            modelBuilder.Entity<Order>()
                .HasIndex(o => o.Status);
        }
    }
}