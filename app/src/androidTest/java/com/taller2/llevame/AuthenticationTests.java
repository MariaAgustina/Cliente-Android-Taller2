package com.taller2.llevame;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.facebook.AccessToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AuthenticationTests {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.taller2.llevame", appContext.getPackageName());
    }

    @Test
    public void loggedInUserSaved() throws Exception {
        boolean isLoggedIn = (AccessToken.getCurrentAccessToken() != null);
        assertEquals(isLoggedIn,true);
    }
}