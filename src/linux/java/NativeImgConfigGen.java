import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NativeImgConfigGen {

    public static void main(String[] args) throws IOException {
        final JsonArray jni = new JsonArray();
        final String[] allDeclared = {
                "org.eclipse.swt.accessibility.AccessibleObject",
                "org.eclipse.swt.graphics.Device",
                "org.eclipse.swt.internal.accessibility.gtk.AtkActionIface",
                "org.eclipse.swt.internal.accessibility.gtk.AtkAttribute",
                "org.eclipse.swt.internal.accessibility.gtk.AtkComponentIface",
                "org.eclipse.swt.internal.accessibility.gtk.AtkEditableTextIface",
                "org.eclipse.swt.internal.accessibility.gtk.AtkHypertextIface",
                "org.eclipse.swt.internal.accessibility.gtk.AtkObjectClass",
                "org.eclipse.swt.internal.accessibility.gtk.AtkSelectionIface",
                "org.eclipse.swt.internal.accessibility.gtk.AtkTableIface",
                "org.eclipse.swt.internal.accessibility.gtk.AtkTextIface",
                "org.eclipse.swt.internal.accessibility.gtk.AtkTextRange",
                "org.eclipse.swt.internal.accessibility.gtk.AtkTextRectangle",
                "org.eclipse.swt.internal.accessibility.gtk.AtkValueIface",
                "org.eclipse.swt.internal.cairo.cairo_path_data_t",
                "org.eclipse.swt.internal.cairo.cairo_path_t",
                "org.eclipse.swt.internal.cairo.cairo_rectangle_int_t",
                "org.eclipse.swt.internal.gtk.GdkEvent",
                "org.eclipse.swt.internal.gtk.GdkEventButton",
                "org.eclipse.swt.internal.gtk.GdkEventCrossing",
                "org.eclipse.swt.internal.gtk.GdkEventFocus",
                "org.eclipse.swt.internal.gtk.GdkEventKey",
                "org.eclipse.swt.internal.gtk.GdkEventMotion",
                "org.eclipse.swt.internal.gtk.GdkEventWindowState",
                "org.eclipse.swt.internal.gtk.GdkGeometry",
                "org.eclipse.swt.internal.gtk.GdkKeymapKey",
                "org.eclipse.swt.internal.gtk.GdkRectangle",
                "org.eclipse.swt.internal.gtk.GdkRGBA",
                "org.eclipse.swt.internal.gtk.GdkWindowAttr",
                "org.eclipse.swt.internal.gtk.GObjectClass",
                "org.eclipse.swt.internal.gtk.GtkAdjustment",
                "org.eclipse.swt.internal.gtk.GtkAllocation",
                "org.eclipse.swt.internal.gtk.GtkBorder",
                "org.eclipse.swt.internal.gtk.GtkCellRendererClass",
                "org.eclipse.swt.internal.gtk.GtkRequisition",
                "org.eclipse.swt.internal.gtk.GtkTargetEntry",
                "org.eclipse.swt.internal.gtk.GtkWidgetClass",
                "org.eclipse.swt.internal.gtk.GTypeInfo",
                "org.eclipse.swt.internal.gtk.PangoAttrColor",
                "org.eclipse.swt.internal.gtk.PangoAttribute",
                "org.eclipse.swt.internal.gtk.PangoAttrInt",
                "org.eclipse.swt.internal.gtk.PangoItem",
                "org.eclipse.swt.internal.gtk.PangoLayoutLine",
                "org.eclipse.swt.internal.gtk.PangoLayoutRun",
                "org.eclipse.swt.internal.gtk.PangoLogAttr",
                "org.eclipse.swt.internal.gtk.PangoRectangle",
                "org.eclipse.swt.internal.gtk.XAnyEvent",
                "org.eclipse.swt.internal.gtk.XEvent",
                "org.eclipse.swt.internal.gtk.XExposeEvent",
                "org.eclipse.swt.internal.gtk.XFocusChangeEvent",
                "org.eclipse.swt.widgets.Control",
                "org.eclipse.swt.widgets.Display",
                "org.eclipse.swt.widgets.Dialog",
                "org.eclipse.swt.internal.gtk.OS",
                "org.eclipse.swt.internal.SessionManagerDBus"
        };

        final String[] opts = {
                "allDeclaredConstructors",
                "allDeclaredMethods",
                "allDeclaredFields"
        };

        for (String s : allDeclared) {
            JsonObject e = new JsonObject();
            e.addProperty("name", s);
            for (String opt : opts) {
                e.addProperty(opt, true);
            }
            jni.add(e);
        }

        Files.write(Paths.get(args[0]), new GsonBuilder().setPrettyPrinting().create().toJson(jni).getBytes(StandardCharsets.UTF_8));
    }
}
