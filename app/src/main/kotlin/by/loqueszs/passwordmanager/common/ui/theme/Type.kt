package by.loqueszs.passwordmanager.common.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import by.loqueszs.passwordmanager.R

private val nunito = FontFamily(
    Font(R.font.nunito_regular),
    Font(R.font.nunito_semi_bold, FontWeight.SemiBold),
    Font(R.font.nunito_bold, FontWeight.Bold),
    Font(R.font.nunito_extra_bold, FontWeight.ExtraBold)
)

// Set of Material typography styles to start with
val Typography = Typography(

    /**
     Display

     There are three display styles in the default type scale:
     Large, medium, and small.
     As the largest text on the screen, display styles are reserved for short, important text or numerals.
     They work best on large screens.
     For display type, consider choosing a more expressive font, such as a handwritten or script style.
     If available, set the appropriate optical size to your usage.
     */
    displayLarge = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 72.sp,
        lineHeight = 72.sp,
        letterSpacing = 1.sp
    ),
    displayMedium = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 64.sp,
        lineHeight = 64.sp,
        letterSpacing = 1.sp
    ),
    displaySmall = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 56.sp,
        lineHeight = 56.sp,
        letterSpacing = 1.sp
    ),

    /**
     Headline

     Headlines are best-suited for short, high-emphasis text on smaller screens.
     These styles can be good for marking primary passages of text or important regions of content.
     Headlines can also make use of expressive typefaces, provided that appropriate line height
     and letter spacing is also integrated to maintain readability.
     * */

    headlineLarge = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp,
        lineHeight = 48.sp,
        letterSpacing = 1.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        lineHeight = 40.sp,
        letterSpacing = 1.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 32.sp,
        letterSpacing = 1.sp
    ),

    /**
     Title

     Titles are smaller than headline styles, and should be used for medium-emphasis text that remains relatively short.
     For example, consider using title styles to divide secondary passages of text or secondary regions of content.
     For titles, use caution when using expressive fonts, including display, handwritten, and script styles.
     **/

    titleLarge = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 24.sp,
        letterSpacing = 1.sp
    ),
    titleMedium = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 20.sp,
        letterSpacing = 1.sp
    ),
    titleSmall = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 1.sp
    ),

    /**
     * Body

     Body styles are used for longer passages of text in your app.
     Use typefaces intended for body styles, which are readable at smaller sizes and can be comfortably read in longer passages.
     Avoid expressive or decorative fonts for body text because these can be harder to read at small sizes.
     */

    bodyLarge = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 1.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 14.sp,
        letterSpacing = 1.sp
    ),
    bodySmall = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 12.sp,
        letterSpacing = 1.sp
    ),

    /**
     Label

     Label styles are smaller, utilitarian styles,
     used for things like the text inside components or for very small text in the content body, such as captions.
     Buttons, for example, use the label large style.*/

    labelLarge = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 12.sp,
        letterSpacing = 1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 10.sp,
        letterSpacing = 1.sp
    ),
    labelSmall = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.Medium,
        fontSize = 8.sp,
        lineHeight = 8.sp,
        letterSpacing = 1.sp
    )
)