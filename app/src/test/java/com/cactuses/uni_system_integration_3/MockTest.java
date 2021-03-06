package com.cactuses.uni_system_integration_3;

import android.content.Context;
import android.support.v4.app.FragmentActivity;


import com.cactuses.uni_system_integration_3.formocks.ClientApp;
import com.cactuses.uni_system_integration_3.formocks.FakeServer;
import com.cactuses.uni_system_integration_3.formocks.ScreenView;
import com.cactuses.uni_system_integration_3.ui.adapter.VideoAdapter;
import com.cactuses.uni_system_integration_3.utils.AuthHelper;
import com.cactuses.uni_system_integration_3.utils.AuthView;
import com.cactuses.uni_system_integration_3.utils.PrefsKeyValueStorage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static junit.framework.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class MockTest {
    private FragmentActivity mMockActivity;
    private AuthView mMockAuthView;
    private AuthHelper mAuthHelper;
	
	private static final String FAKE_STRING = "FAKESTR";	

    @Before
    public void setUp() throws Exception {
        mMockActivity = Mockito.mock(FragmentActivity.class);
        mMockAuthView = Mockito.mock(AuthView.class);
        mAuthHelper = new AuthHelper(mMockActivity, mMockAuthView);
    }

    @Test
    public void testConstructor() throws Exception {
        assertNotNull(mAuthHelper);
    }
    
  @Test
    public void testServerRequestSuccess() {
        FakeServer mockServer = Mockito.mock(FakeServer.class);
        ScreenView mockView = Mockito.mock(ScreenView.class);
        ClientApp app = new ClientApp(mockServer, mockView);
        when(mockServer.fetchImages()).thenReturn(true);

        app.launch();
        verify(mockView).showImages();
    }
 @Test
    public void testServerRequestFailed() {
        FakeServer mockServer = Mockito.mock(FakeServer.class);
        ScreenView mockView = Mockito.mock(ScreenView.class);
        ClientApp app = new ClientApp(mockServer, mockView);
        when(mockServer.fetchImages()).thenReturn(false);

        app.launch();
        verify(mockView).showError();
    }
    @Test
    public void testUserAuthorizedTrue() {
        FakeServer mockServer = Mockito.mock(FakeServer.class);
        ScreenView mockView = Mockito.mock(ScreenView.class);
        ClientApp app = new ClientApp(mockServer, mockView);

        app.isAuthorized("sd456qwertttt9876fghj");
        verify(mockView).showLoading();
        verify(mockView, times(0)).openLoginForm();


    }
    @Test
    public void testUserAuthorizedFalse() {
        FakeServer mockServer = Mockito.mock(FakeServer.class);
        ScreenView mockView = Mockito.mock(ScreenView.class);
        ClientApp app = new ClientApp(mockServer, mockView);

        app.isAuthorized("");
        verify(mockView).openLoginForm();
        Mockito.verifyNoMoreInteractions(mockView);

    }	


    @Test
    public void readStringFromContext() {
        Context mockContext = Mockito.mock(Context.class);
        when(mockContext.getString(R.string.app_name)).thenReturn(FAKE_STRING);
        VideoAdapter va = new VideoAdapter(mockContext, new ArrayList<>());
        String result = va.getSomeString();
        assertThat(result, is(FAKE_STRING));

    }

    @Test
    public void testExactArguments() {
        Context mockContext = Mockito.mock(Context.class);
        VideoAdapter va = new VideoAdapter(mockContext, new ArrayList<>());
        va.getSomeString();
        verify(mockContext).getString(R.string.app_name);

    }


}
