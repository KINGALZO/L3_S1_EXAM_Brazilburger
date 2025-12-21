using Microsoft.AspNetCore.Mvc;
using BrasilBurger.Web.Data;
using BrasilBurger.Web.Helpers;
using BrasilBurger.Web.Models;
using Microsoft.AspNetCore.Authorization;

namespace BrasilBurger.Web.Controllers
{
    public class CartController : Controller
    {
        private readonly ApplicationDbContext _context;
        private const string SessionKey = "Cart";

        public CartController(ApplicationDbContext context)
        {
            _context = context;
        }

        [HttpPost]
        [AllowAnonymous]
        public IActionResult AddToCart(int id, string type = "Burger", int qty = 1)
        {
            var cart = HttpContext.Session.GetObject<List<CartItem>>(SessionKey) ?? new List<CartItem>();
            if (type == "Burger")
            {
                var burger = _context.Burgers.Find(id);
                if (burger == null) return NotFound();
                var existing = cart.FirstOrDefault(c => c.ProductId == id && c.ProductType == "Burger");
                if (existing != null) existing.Quantity += qty;
                else cart.Add(new CartItem { ProductId = id, ProductType = "Burger", Name = burger.Name, ImageUrl = burger.ImageUrl, UnitPrice = burger.Price, Quantity = qty });
            }
            else
            {
                var menu = _context.Menus.Find(id);
                if (menu == null) return NotFound();
                var existing = cart.FirstOrDefault(c => c.ProductId == id && c.ProductType == "Menu");
                if (existing != null) existing.Quantity += qty;
                else cart.Add(new CartItem { ProductId = id, ProductType = "Menu", Name = menu.Name, ImageUrl = menu.ImageUrl, UnitPrice = menu.Price, Quantity = qty });
            }

            HttpContext.Session.SetObject(SessionKey, cart);
            return Redirect(Request.Headers["Referer"].ToString() ?? "/");
        }

        [HttpGet]
        [AllowAnonymous]
        public IActionResult Index()
        {
            var cart = HttpContext.Session.GetObject<List<CartItem>>(SessionKey) ?? new List<CartItem>();
            ViewBag.Total = cart.Sum(c => c.SubTotal);
            return View(cart);
        }

        [HttpPost]
        [AllowAnonymous]
        public IActionResult Remove(int productId, string productType)
        {
            var cart = HttpContext.Session.GetObject<List<CartItem>>(SessionKey) ?? new List<CartItem>();
            cart.RemoveAll(c => c.ProductId == productId && c.ProductType == productType);
            HttpContext.Session.SetObject(SessionKey, cart);
            return RedirectToAction("Index");
        }

        [HttpPost]
        [AllowAnonymous]
        public IActionResult UpdateQuantity(int productId, string productType, int qty)
        {
            var cart = HttpContext.Session.GetObject<List<CartItem>>(SessionKey) ?? new List<CartItem>();
            var item = cart.FirstOrDefault(c => c.ProductId == productId && c.ProductType == productType);
            if (item != null)
            {
                item.Quantity = Math.Max(1, qty);
                HttpContext.Session.SetObject(SessionKey, cart);
            }
            return RedirectToAction("Index");
        }

        [HttpGet]
        [Authorize]
        public IActionResult Checkout()
        {
            var cart = HttpContext.Session.GetObject<List<CartItem>>(SessionKey) ?? new List<CartItem>();
            if (!cart.Any()) return RedirectToAction("Index");
            ViewBag.Total = cart.Sum(c => c.SubTotal);
            return View(cart);
        }

        [HttpPost]
        [Authorize]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Checkout(string deliveryType, string? address, string paymentMethod)
        {
            var cart = HttpContext.Session.GetObject<List<CartItem>>(SessionKey) ?? new List<CartItem>();
            if (!cart.Any()) return RedirectToAction("Index");

            // get current user
            var userIdStr = User.FindFirst(System.Security.Claims.ClaimTypes.NameIdentifier)?.Value;
            if (!int.TryParse(userIdStr, out var userId)) return Challenge();
            var client = _context.Clients.Find(userId);
            if (client == null) return Challenge();

            var order = new Order
            {
                ClientId = client.Id,
                DeliveryType = deliveryType,
                DeliveryAddress = address,
                TotalAmount = cart.Sum(c => c.SubTotal),
                Status = "Validé",
                OrderDate = DateTime.Now
            };

            _context.Orders.Add(order);
            await _context.SaveChangesAsync();

            foreach (var it in cart)
            {
                var item = new OrderItem
                {
                    OrderId = order.Id,
                    Quantity = it.Quantity,
                    UnitPrice = it.UnitPrice
                };

                if (it.ProductType == "Burger") item.BurgerId = it.ProductId;
                else item.MenuId = it.ProductId;

                _context.OrderItems.Add(item);
            }

            var payment = new Payment
            {
                OrderId = order.Id,
                Amount = order.TotalAmount,
                PaymentDate = DateTime.Now,
                PaymentMethod = paymentMethod
            };

            _context.Payments.Add(payment);
            await _context.SaveChangesAsync();

            // clear cart
            HttpContext.Session.Remove(SessionKey);

            return RedirectToAction("Index", "Home");
        }
    }
}