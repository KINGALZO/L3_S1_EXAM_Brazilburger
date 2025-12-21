using System.ComponentModel.DataAnnotations;

namespace BrasilBurger.Web.Models
{
    public class Order
    {
        public int Id { get; set; }
        public int ClientId { get; set; }
        public Client Client { get; set; }
        public DateTime OrderDate { get; set; } = DateTime.Now;
        public decimal TotalAmount { get; set; }
        public string Status { get; set; } = "En attente"; // En attente, Validé, En préparation, Prêt, Terminé, Annulé
        public string DeliveryType { get; set; } // Sur place, À emporter, Livraison
        public string DeliveryAddress { get; set; }
        public string Notes { get; set; }
        
        // Relations
        public ICollection<OrderItem> OrderItems { get; set; }
        public Payment Payment { get; set; }
    }
}