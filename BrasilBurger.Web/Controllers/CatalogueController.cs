using Microsoft.AspNetCore.Mvc;
using BrasilBurger.Web.Data;
using Microsoft.EntityFrameworkCore;
using BrasilBurger.Web.Models;

namespace BrasilBurger.Web.Controllers
{
    public class CatalogueController : Controller
    {
        private readonly ApplicationDbContext _context;
        public CatalogueController(ApplicationDbContext context)
        {
            _context = context;
        }

        [HttpGet]
        [AllowAnonymous]
        public async Task<IActionResult> Details(int id, string type = "Burger")
        {
            if (type == null) type = "Burger";
            if (type.Equals("Menu", StringComparison.OrdinalIgnoreCase))
            {
                var menu = await _context.Menus.FirstOrDefaultAsync(m => m.Id == id && m.IsActive);
                if (menu == null) return NotFound();
                return View("DetailsMenu", menu);
            }

            var burger = await _context.Burgers.FirstOrDefaultAsync(b => b.Id == id && b.IsActive);
            if (burger == null) return NotFound();
            return View("DetailsBurger", burger);
        }
    }
}
