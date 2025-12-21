namespace BrasilBurger.Web.Models
{
    public class Payment
    {
        public int Id { get; set; }
        public int OrderId { get; set; }
        public Order Order { get; set; }
        public decimal Amount { get; set; }
        public DateTime PaymentDate { get; set; } = DateTime.Now;
        public string PaymentMethod { get; set; } // Wave, OM
        public string TransactionId { get; set; }
        public string Status { get; set; } = "Complété";
    }
}