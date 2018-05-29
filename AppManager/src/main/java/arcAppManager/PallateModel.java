package arcAppManager;

import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.widget.LinearLayout;

public class PallateModel {
    private void setUpInfoBackgroundColor(LinearLayout ll, Palette palette) {
        Palette.Swatch swatch = getMostPopulousSwatch(palette);
        if (swatch != null) {
            int startColor = ContextCompat.getColor(ll.getContext(), android.R.color.darker_gray);
            int endColor = swatch.getRgb();

            //AnimationUtility.animateBackgroundColorChange(ll, startColor, endColor);
        }
    }

    public static Palette.Swatch getMostPopulousSwatch(Palette palette) {
        Palette.Swatch mostPopulous = null;
        if (palette != null) {
            for (Palette.Swatch swatch : palette.getSwatches()) {
                if (mostPopulous == null || swatch.getPopulation() > mostPopulous.getPopulation()) {
                    mostPopulous = swatch;
                }
            }
        }
        return mostPopulous;
    }

    public Palette posterPalette;

    public PallateModel() {
    }

    public Palette getPosterPalette() {
        return posterPalette;
    }

    public void setPosterPalette(Palette posterPalette) {
        this.posterPalette = posterPalette;
    }
}
