namespace BrasilBurger.Web.Models
{
    public class Menu
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public string ImageUrl { get; set; } = "/images/default-menu.jpg";
        public string CloudinaryPublicId { get; set; }
        public decimal Price { get; set; }
        public bool IsActive { get; set; } = true;
        public bool IsFeatured { get; set; }
        
        // Relations
        public int BurgerId { get; set; }
        public Burger Burger { get; set; }
        
        public int? SupplementId { get; set; }
        public Supplement Supplement { get; set; }
        
        public int? DrinkId { get; set; }
        public Supplement Drink { get; set; }
        
        public DateTime CreatedAt { get; set; } = DateTime.Now;
    }
}