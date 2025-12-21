using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using BrasilBurger.Web.Data;

namespace BrasilBurger.Web.Controllers
{
    public class HomeController : Controller
    {
        private readonly ApplicationDbContext _context;
        
        public HomeController(ApplicationDbContext context)
        {
            _context = context;
        }
        
        public async Task<IActionResult> Index()
        {
            try
            {
                var burgers = await _context.Burgers
                    .Where(b => b.IsActive)
                    .OrderByDescending(b => b.CreatedAt)
                    .Take(12)
                    .ToListAsync();
                
                ViewBag.Burgers = burgers;
                ViewBag.BurgerCount = await _context.Burgers.CountAsync();
                ViewBag.DatabaseStatus = "✅ Connecté à Neon PostgreSQL";
            }
            catch (Exception)
            {
                ViewBag.DatabaseStatus = "❌ Erreur de connexion";
                ViewBag.Burgers = new List<Burger>();
                ViewBag.BurgerCount = 0;
            }
            
            return View();
        }
        
        public IActionResult Privacy()
        {
            return View();
        }
    }
}