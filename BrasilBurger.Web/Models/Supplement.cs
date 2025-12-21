namespace BrasilBurger.Web.Models
{
    public class Supplement
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public decimal Price { get; set; }
        public string Type { get; set; } // "Frites", "Boisson", "Sauce"
        public string ImageUrl { get; set; }
        public bool IsActive { get; set; } = true;
        public DateTime CreatedAt { get; set; } = DateTime.Now;
    }
}