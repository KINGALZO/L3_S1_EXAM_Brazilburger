using System.Security.Claims;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Identity;
using BrasilBurger.Web.Data;
using BrasilBurger.Web.Models;
using Microsoft.AspNetCore.Authorization;

namespace BrasilBurger.Web.Controllers
{
    public class AccountController : Controller
    {
        private readonly ApplicationDbContext _context;
        private readonly PasswordHasher<User> _hasher = new PasswordHasher<User>();

        public AccountController(ApplicationDbContext context)
        {
            _context = context;
        }

        [HttpGet]
        [AllowAnonymous]
        public IActionResult Register()
        {
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        [AllowAnonymous]
        public async Task<IActionResult> Register(Client model)
        {
            if (!ModelState.IsValid) return View(model);

            var exists = _context.Clients.Any(c => c.Email == model.Email);
            if (exists)
            {
                ModelState.AddModelError("", "Un compte utilise déjà cet email.");
                return View(model);
            }

            // Hash password
            model.PasswordHash = _hasher.HashPassword(model, model.Password);
            model.Password = null;
            _context.Clients.Add(model);
            await _context.SaveChangesAsync();

            return RedirectToAction(nameof(Login));
        }

        [HttpGet]
        [AllowAnonymous]
        public IActionResult Login(string? returnUrl = null)
        {
            ViewBag.ReturnUrl = returnUrl;
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        [AllowAnonymous]
        public async Task<IActionResult> Login(string email, string password, string? returnUrl = null)
        {
            var user = _context.Clients.FirstOrDefault(c => c.Email == email);
            if (user == null)
            {
                ModelState.AddModelError("","Email ou mot de passe invalide.");
                return View();
            }

            var result = _hasher.VerifyHashedPassword(user, user.PasswordHash ?? "", password);
            if (result == PasswordVerificationResult.Failed)
            {
                ModelState.AddModelError("","Email ou mot de passe invalide.");
                return View();
            }

            var claims = new List<Claim>
            {
                new Claim(ClaimTypes.NameIdentifier, user.Id.ToString()),
                new Claim(ClaimTypes.Email, user.Email),
                new Claim(ClaimTypes.Name, user.FirstName + " " + user.LastName)
            };

            var identity = new ClaimsIdentity(claims, CookieAuthenticationDefaults.AuthenticationScheme);
            var principal = new ClaimsPrincipal(identity);

            await HttpContext.SignInAsync(CookieAuthenticationDefaults.AuthenticationScheme, principal);

            if (!string.IsNullOrEmpty(returnUrl) && Url.IsLocalUrl(returnUrl))
                return Redirect(returnUrl);

            return RedirectToAction("Index", "Home");
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Logout()
        {
            await HttpContext.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
            return RedirectToAction("Login");
        }
    }
}