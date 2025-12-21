namespace BrasilBurger.Web.Models
{
    public class CartItem
    {
        public int ProductId { get; set; }
        public string ProductType { get; set; } = "Burger"; // Burger|Menu
        public string Name { get; set; }
        public string ImageUrl { get; set; }
        public decimal UnitPrice { get; set; }
        public int Quantity { get; set; } = 1;

        public decimal SubTotal => UnitPrice * Quantity;
    }
}