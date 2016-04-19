package com.massiva.nuevopudahuel.controllers;

import com.bzutils.LogBZ;
import com.massiva.nuevopudahuel.base.PudahuelApplication;
import com.massiva.nuevopudahuel.constants.PudahuelPrefs;
import com.massiva.nuevopudahuel.model.ConfigCategory;
import com.google.gson.Gson;
import com.innoquant.moca.MOCA;
import com.innoquant.moca.MOCACallback;
import com.innoquant.moca.MOCAException;
import com.innoquant.moca.MOCAInstance;
import com.innoquant.moca.MOCAUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by iaguila on 22/3/16.
 */
public class ConfigurationCategoriesController {

    private static final String kCategory = "categoryKey";
    private static final String kAll                = kCategory + "All";
    private static final String kGeneral            = kCategory + "General";
    private static final String kTourist            = kCategory + "Turismo";
    private static final String kTransport          = kCategory + "Transporte";
    private static final String kHotel              = kCategory + "Hotel";
    private static final String kHouseChange        = kCategory + "CasasCambio";
    private static final String kTravel             = kCategory + "AsistenciaViaje";
    private static final String kChildren           = kCategory + "JardinInfantil";
    private static final String kBank               = kCategory + "Banco";
    private static final String kPhone              = kCategory + "Telefonia";
    private static final String kHealth             = kCategory + "Salud";
    private static final String kPharmacy           = kCategory + "Farmacias";
    private static final String kRestaurants        = kCategory + "Restaurantes";
    private static final String kPersonalHealth     = kCategory + "CuidadoPersonal";
    private static final String kMusic              = kCategory + "Musica";
    private static final String kTecnology          = kCategory + "Tecnologia";
    private static final String kWines              = kCategory + "VinosLicores";
    private static final String kToys               = kCategory + "Jugueterias";
    private static final String kMinimarket         = kCategory + "Minimarket";
    private static final String kDressing           = kCategory + "VestuarioAccesorios";
    private static final String kChocolateDutyFre   = kCategory + "ChocolateriaDutyFree";

    private static final String[] categoryKeys = {  kGeneral, kAll, kTourist, kTransport, kHotel, kHouseChange, kTravel, kChildren, kBank, kPhone, kHealth, kPharmacy,
                                                    kRestaurants, kPersonalHealth, kMusic, kTecnology, kWines, kToys, kMinimarket, kDressing, kChocolateDutyFre
                                                 };


    private static ConfigurationCategoriesController ourInstance = new ConfigurationCategoriesController();

    public static ConfigurationCategoriesController getInstance() {
        return ourInstance;
    }

    private ConfigurationCategoriesController() {
    }

    public void initCategoriesConfig(final PudahuelApplication application) {
        uploadCategoriesToMOCA(application, getDefaultCategories());
    }

    public void uploadCategoriesToMOCA(final PudahuelApplication application, List<ConfigCategory> categories) {
        MOCAUser user = MOCA.getInstance().login(UserController.getInstance().getStoredGoogleAID(application));
        for (ConfigCategory category : categories) {
            user.setProperty(category.getKey(), category.isValue());
        }
        user.save(new MOCACallback<MOCAUser>() {
            @Override
            public void success(MOCAUser mocaUser) {
                storeCategories(application, mocaUser);
            }

            @Override
            public void failure(MOCAException e) {
                e.printStackTrace();
            }
        });
    }

    public void getCategoriesConfig(final PudahuelApplication application) {
        MOCA.getInstance().login(UserController.getInstance().getStoredGoogleAID(application));
        MOCA.getInstance().save(new MOCACallback<MOCAInstance>() {
            @Override
            public void success(MOCAInstance mocaInstance) {
                storeCategories(application, mocaInstance.login(UserController.getInstance().getStoredGoogleAID(application)));
            }

            @Override
            public void failure(MOCAException e) {

            }
        });
    }

    public boolean checkMOCAConfig(final PudahuelApplication application) {
        MOCAUser user = MOCA.getInstance().login(UserController.getInstance().getStoredGoogleAID(application));
        if (user.getProperty(categoryKeys[0]) == null) {
            initCategoriesConfig(application);
            return false;
        }
        return true;
    }

    private void storeCategories(PudahuelApplication application, MOCAUser user) {
        try {
            List<ConfigCategory> categories = new ArrayList<>();
            for (String key : categoryKeys) {
                ConfigCategory category = new ConfigCategory();
                category.setKey(key);
                category.setValue(user.getBoolProperty(key));
                categories.add(category);
            }
            application.storeString(PudahuelPrefs.CONFIG_CATEGORIES, new Gson().toJson(categories));
        } catch (Exception e) {
            LogBZ.printStackTrace(e);
        }
    }

    public List<ConfigCategory> getStoredCategories(PudahuelApplication application) {
        ConfigCategory[] storedCategories = new Gson().fromJson(application.getStoredString(PudahuelPrefs.CONFIG_CATEGORIES, "[]"), ConfigCategory[].class);
        return Arrays.asList(storedCategories);
    }

    private List<ConfigCategory> getDefaultCategories() {
        List<ConfigCategory> categories = new ArrayList<>();
        for (String key : categoryKeys) {
            ConfigCategory category = new ConfigCategory();
            category.setKey(key);
            category.setValue(true);
            categories.add(category);
        }
        return categories;
    }
}
