**Vesta 1.6.0 — Network tab for your OpenWrt router**

- **Open router settings:** the Deco button is now an **Open router settings (LuCI)** button that goes straight to your OpenWrt admin page at 192.168.1.1.
- **Internet status** comes from a live check on your phone, so it works without any router integration.
- **Connected devices:** once the OpenWrt (LuCI) or Ubus integration is added in Home Assistant, every device on your Wi-Fi shows here with its IP and online status.
- **Live speeds and data use** return automatically once UPnP is turned on in OpenWrt — the tab shows exactly how.
- Old Deco-only sections are hidden.

Also: Vesta finds Home Assistant by itself after an address change (1.5.2), live updates, pull to sync and haptics.
