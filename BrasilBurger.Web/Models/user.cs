using System.ComponentModel.DataAnnotations;

namespace BrasilBurger.Web.Models
{
    public abstract class User
    {
        public int Id { get; set; }
        
        [Required(ErrorMessage = "L'email est requis")]
        [EmailAddress(ErrorMessage = "Email invalide")]
        public string Email { get; set; }
        
        [Required(ErrorMessage = "Le mot de passe est requis")]
        public string Password { get; set; }
        
        public DateTime CreatedAt { get; set; } = DateTime.Now;
        public bool IsActive { get; set; } = true;
    }
}