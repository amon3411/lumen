**Vesta 1.7.0 — Control your OpenWrt router**

Sign in to your router from the Network tab (same login as LuCI) and Vesta talks to OpenWrt directly — no Home Assistant integration needed.

- **Router status:** internet online/offline, WAN IP, uptime, memory and load, refreshed every 10 seconds.
- **Devices:** everyone on your network with IP, MAC and Wi-Fi signal. **Kick** a device off Wi-Fi or **Block** its internet (tap twice to confirm); **Unblock** any time.
- **Wi-Fi:** turn each network on or off, and change its name or password.
- **Router actions:** restart Wi-Fi, reconnect the internet, reboot the router.

iPhone/browser users: run `uci set uhttpd.main.ubus_cors=1; uci commit uhttpd; /etc/init.d/uhttpd restart` on the router once so Vesta can reach it.
