using Microsoft.AspNetCore.Mvc;
using BrasilBurger.Web.Data;
using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Authorization;

namespace BrasilBurger.Web.Controllers
{
    public class DebugController : Controller
    {
        private readonly ApplicationDbContext _context;
        public DebugController(ApplicationDbContext context)
        {
            _context = context;
        }

        [HttpGet("/debug/status")]
        [AllowAnonymous]
        public async Task<IActionResult> Status(string key)
        {
            var expected = Environment.GetEnvironmentVariable("DIAGNOSTIC_KEY");
            if (string.IsNullOrEmpty(expected) || key != expected)
            {
                return Forbid();
            }

            try
            {
                var canConnect = await _context.Database.CanConnectAsync();
                var burgers = await _context.Burgers.CountAsync();
                var menus = await _context.Menus.CountAsync();
                var clients = await _context.Clients.CountAsync();
                var orders = await _context.Orders.CountAsync();

                return Json(new
                {
                    env = Environment.GetEnvironmentVariable("ASPNETCORE_ENVIRONMENT") ?? "Unknown",
                    canConnect,
                    counts = new { burgers, menus, clients, orders }
                });
            }
            catch (Exception ex)
            {
                return StatusCode(500, new { error = ex.Message });
            }
        }

        [HttpGet("/debug/ping")]
        [AllowAnonymous]
        public IActionResult Ping()
        {
            return Json(new { status = "ok", time = DateTime.UtcNow });
        }
    }
}