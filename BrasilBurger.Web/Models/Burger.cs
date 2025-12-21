using System.ComponentModel.DataAnnotations;

namespace BrasilBurger.Web.Models
{
    public class Burger
    {
        public int Id { get; set; }
        
        [Required]
        [StringLength(100)]
        public string Name { get; set; }
        
        [Required]
        [Range(0, 100000)]
        public decimal Price { get; set; }
        
        [StringLength(500)]
        public string Description { get; set; }
        
        public string ImageUrl { get; set; } = "/images/default-burger.jpg";
        public string CloudinaryPublicId { get; set; }
        
        public bool IsActive { get; set; } = true;
        public bool IsPopular { get; set; }
        public DateTime CreatedAt { get; set; } = DateTime.Now;
        public string Tags { get; set; }
    }
}