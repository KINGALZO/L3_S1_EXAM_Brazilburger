using Microsoft.AspNetCore.Mvc;

namespace BrasilBurger.Web.Controllers
{
    // Backwards compatibility: redirect old /Auth routes to /Account
    [Route("Auth")]
    public class AuthController : Controller
    {
        [HttpGet("")]
        public IActionResult Index()
        {
            return RedirectToAction("Login", "Account");
        }

        [HttpGet("Login")]
        public IActionResult Login(string? returnUrl = null)
        {
            return RedirectToAction("Login", "Account", new { returnUrl });
        }

        [HttpGet("Register")]
        public IActionResult Register()
        {
            return RedirectToAction("Register", "Account");
        }
    }
}
