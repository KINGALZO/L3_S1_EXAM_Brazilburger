using System.ComponentModel.DataAnnotations;

namespace BrasilBurger.Web.Models
{
    public class Client : User
    {
        [Required(ErrorMessage = "Le prénom est requis")]
        [StringLength(50)]
        public string FirstName { get; set; }
        
        [Required(ErrorMessage = "Le nom est requis")]
        [StringLength(50)]
        public string LastName { get; set; }
        
        [Required(ErrorMessage = "Le téléphone est requis")]
        [Phone(ErrorMessage = "Numéro invalide")]
        public string Phone { get; set; }
        
        public string Address { get; set; }
        
        public ICollection<Order> Orders { get; set; }
    }
}