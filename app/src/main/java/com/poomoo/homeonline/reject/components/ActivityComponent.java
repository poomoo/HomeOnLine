/*
 *  Copyright (C) 2015 GuDong <gudong.name@gmail.com>
 *
 *  This file is part of GdTranslate
 *
 *  GdTranslate is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  GdTranslate is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with GdTranslate.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.poomoo.homeonline.reject.components;

import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.activity.AbroadActivity;
import com.poomoo.homeonline.ui.activity.AbroadClassifyListActivity;
import com.poomoo.homeonline.ui.activity.AbroadCountryActivity;
import com.poomoo.homeonline.ui.activity.AbroadCountryInfoActivity;
import com.poomoo.homeonline.ui.activity.AbroadGlobalActivity;
import com.poomoo.homeonline.ui.activity.AddressInfoActivity;
import com.poomoo.homeonline.ui.activity.AddressListActivity;
import com.poomoo.homeonline.ui.activity.ChangePassWordActivity;
import com.poomoo.homeonline.ui.activity.ClassifyInfoActivity;
import com.poomoo.homeonline.ui.activity.ClassifyListActivity;
import com.poomoo.homeonline.ui.activity.CollectActivity;
import com.poomoo.homeonline.ui.activity.CommodityInfoActivity;
import com.poomoo.homeonline.ui.activity.ConfirmOrderActivity;
import com.poomoo.homeonline.ui.activity.EvaluateActivity;
import com.poomoo.homeonline.ui.activity.FeedBackActivity;
import com.poomoo.homeonline.ui.activity.GetCodeActivity;
import com.poomoo.homeonline.ui.activity.NewAbroadActivity;
import com.poomoo.homeonline.ui.activity.TicketZoneActivity;
import com.poomoo.homeonline.ui.activity.InviteActivity;
import com.poomoo.homeonline.ui.activity.LogInActivity;
import com.poomoo.homeonline.ui.activity.MyTicketActivity;
import com.poomoo.homeonline.ui.activity.PresentActivity;
import com.poomoo.homeonline.ui.activity.ReFundActivity;
import com.poomoo.homeonline.ui.activity.ReFundInfoActivity;
import com.poomoo.homeonline.ui.activity.ScanHistoryActivity;
import com.poomoo.homeonline.ui.activity.SearchActivity;
import com.poomoo.homeonline.ui.activity.SplashActivity;
import com.poomoo.homeonline.ui.activity.TicketActivity;
import com.poomoo.homeonline.ui.activity.UpdateNickNameActivity;
import com.poomoo.homeonline.ui.activity.WebViewDataActivity;

import dagger.Component;

/**
 * Created by GuDong on 2/28/16 10:42.
 * Contact with gudong.name@gmail.com.
 */
@Component(modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(LogInActivity activity);

    void inject(GetCodeActivity activity);

    void inject(ChangePassWordActivity activity);

    void inject(CommodityInfoActivity activity);

    void inject(CollectActivity activity);

    void inject(ScanHistoryActivity activity);

    void inject(AddressListActivity activity);

    void inject(AddressInfoActivity activity);

    void inject(FeedBackActivity activity);

    void inject(UpdateNickNameActivity activity);

    void inject(SearchActivity activity);

    void inject(ClassifyInfoActivity activity);

    void inject(ClassifyListActivity activity);

    void inject(ConfirmOrderActivity activity);

    void inject(ReFundActivity activity);

    void inject(EvaluateActivity activity);

    void inject(ReFundInfoActivity activity);

    void inject(TicketActivity activity);

    void inject(WebViewDataActivity activity);

    void inject(SplashActivity activity);

    void inject(MyTicketActivity activity);

    void inject(InviteActivity activity);

    void inject(TicketZoneActivity activity);

    void inject(PresentActivity activity);

    void inject(AbroadActivity activity);

    void inject(NewAbroadActivity activity);

    void inject(AbroadGlobalActivity activity);

    void inject(AbroadCountryActivity activity);

    void inject(AbroadCountryInfoActivity activity);

    void inject(AbroadClassifyListActivity activity);
}
