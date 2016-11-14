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

import com.poomoo.homeonline.reject.modules.FragmentModule;
import com.poomoo.homeonline.ui.activity.ChangePassWordActivity;
import com.poomoo.homeonline.ui.activity.GetCodeActivity;
import com.poomoo.homeonline.ui.activity.LogInActivity;
import com.poomoo.homeonline.ui.fragment.CartFragment;
import com.poomoo.homeonline.ui.fragment.CenterFragment;
import com.poomoo.homeonline.ui.fragment.ClassifyFragment;
import com.poomoo.homeonline.ui.fragment.InviteListFragment;
import com.poomoo.homeonline.ui.fragment.MainFragment;
import com.poomoo.homeonline.ui.fragment.MyOrdersFragment;
import com.poomoo.homeonline.ui.fragment.RepayListFragment;

import dagger.Component;

/**
 * Created by GuDong on 2/28/16 10:42.
 * Contact with gudong.name@gmail.com.
 */
@Component(modules = {FragmentModule.class})
public interface FragmentComponent {
    void inject(MainFragment fragment);

    void inject(ClassifyFragment fragment);

    void inject(CartFragment fragment);

    void inject(CenterFragment fragment);

    void inject(MyOrdersFragment fragment);

    void inject(InviteListFragment fragment);

    void inject(RepayListFragment fragment);

}
