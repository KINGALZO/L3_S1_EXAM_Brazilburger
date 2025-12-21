using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using BrasilBurger.Web.Data;
using BrasilBurger.Web.Models;
using Microsoft.AspNetCore.Authorization;

namespace BrasilBurger.Web.Controllers
{
    public class HomeController : Controller
    {
        private readonly ApplicationDbContext _context;
        
        public HomeController(ApplicationDbContext context)
        {
            _context = context;
        }
        
        [AllowAnonymous]
        public async Task<IActionResult> Index()
        {
            try
            {
                var burgers = await _context.Burgers
                    .Where(b => b.IsActive)
                    .OrderByDescending(b => b.CreatedAt)
                    .Take(12)
                    .ToListAsync();
                var menus = await _context.Menus
                    .Where(m => m.IsActive)
                    .OrderByDescending(m => m.CreatedAt)
                    .Take(6)
                    .ToListAsync();
                
                ViewBag.Burgers = burgers;
                ViewBag.Menus = menus;
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