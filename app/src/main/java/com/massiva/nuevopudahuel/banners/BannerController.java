package com.massiva.nuevopudahuel.banners;

import com.massiva.nuevopudahuel.api.RestCallback;
import com.massiva.nuevopudahuel.api.requestModel.TokenRequestBody;
import com.massiva.nuevopudahuel.api.response.TokenResponse;
import com.massiva.nuevopudahuel.banners.model.APIBanner;
import com.massiva.nuevopudahuel.base.PudahuelApplication;
import com.massiva.nuevopudahuel.constants.Constants;
import com.massiva.nuevopudahuel.constants.PudahuelPrefs;
import com.massiva.nuevopudahuel.controllers.CarrouselController;
import com.massiva.nuevopudahuel.controllers.UserController;

import java.util.Locale;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by iaguila on 23/3/16.
 */
public class BannerController {
    private static BannerController ourInstance = new BannerController();

    public static BannerController getInstance() {
        return ourInstance;
    }

    private BannerController() {
    }

    public void getAPIToken(final PudahuelApplication application) {
        TokenRequestBody requestBody = new TokenRequestBody(UserController.getInstance().getStoredGoogleAID(application),
                Constants.BANNERS_API_CLIENT_ID, Constants.BANNERS_API_CLIENT_SECRET, Locale.getDefault().getLanguage());
        application.getBannerService().getAccessToken(requestBody, new RestCallback<TokenResponse>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }

            @Override
            public void success(TokenResponse tokenResponse, Response response) {
                super.success(tokenResponse, response);
                storedBannersAPIToken(application, tokenResponse.getAccessToken());
                CarrouselController.getInstance().requestCarrousel(application);
            }
        });
    }

    private void storedBannersAPIToken(PudahuelApplication application, String token) {
        application.storeString(PudahuelPrefs.BANNERS_API_TOKEN, token);
    }

    public String getBannersAPIToken(PudahuelApplication pudahuelApplication) {
        return "Bearer " + pudahuelApplication.getStoredString(PudahuelPrefs.BANNERS_API_TOKEN, null);
    }

    public void loadBanner(PudahuelApplication application, final BannerView bannerView) {
        application.getBannerService().getBanner(getBannersAPIToken(application), bannerView.getScreen(), new RestCallback<APIBanner>() {
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }

            @Override
            public void success(APIBanner banner, Response response) {
                super.success(banner, response);
                bannerView.setBanner(banner);
            }
        });
    }

}
