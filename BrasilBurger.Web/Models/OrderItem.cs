namespace BrasilBurger.Web.Models
{
    public class OrderItem
    {
        public int Id { get; set; }
        public int OrderId { get; set; }
        public Order Order { get; set; }
        
        public int? BurgerId { get; set; }
        public Burger Burger { get; set; }
        
        public int? MenuId { get; set; }
        public Menu Menu { get; set; }
        
        public int? SupplementId { get; set; }
        public Supplement Supplement { get; set; }
        
        public int Quantity { get; set; } = 1;
        public decimal UnitPrice { get; set; }
        
        public decimal SubTotal => Quantity * UnitPrice;
    }
}