**Vesta 1.8.0 — Faster and easier to use**

**Quicker**
- Opens straight to your lights from the last visit while it reconnects, instead of an empty screen.
- Taps reach your lights in about 40 ms (was ~180 ms), and dragging a brightness or color slider now changes the real light while you drag, not only when you let go.
- The screen only redraws what changed, so scrolling stays smooth and buttons no longer miss taps during live updates.
- Connecting to Home Assistant loads everything in parallel; the color wheel is drawn only when first needed; settings save in the background.
- Requests that hang now give up after 10 seconds instead of freezing a control.

**Clearer**
- Each tab has its own title; the light buttons and energy stats only show on the Lights tab.
- Tap the connection status (under the title) to open Home Assistant settings.
- Light controls: brightness and color come first, with room and wattage below, plus a close (×) button.
- Router devices show signal bars first and hide the MAC address once a device has a name.

**Fixed**
- The "Add schedule" form was always showing; it now opens with the button (which becomes Cancel).
- Room choice in the schedule form no longer resets itself while you pick.
