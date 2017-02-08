package birdofparadise.side.dashboard;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import birdofparadise.side.ConfigurationsService.ConfigurationsService;
import birdofparadise.side.LoggingService.LoggingService;
import birdofparadise.side.MessageBoxService.MessageBoxService;
import birdofparadise.side.R;
import birdofparadise.side.UtilService.ColorUtils;
import birdofparadise.side.UtilService.GlobalConstant;
import birdofparadise.side.UtilService.LocaleUtils;
import birdofparadise.side.enums.FragmentEnum;
import birdofparadise.side.fragments.DashboardFragment;
import birdofparadise.side.fragments.OneTimePasswordFragment;
import birdofparadise.side.fragments.baseFragment.BaseFragment;
import birdofparadise.side.fragments.fragmentHelper.FragmentMessageContainer;
import birdofparadise.side.fragments.fragmentHelper.StringContainer;
import birdofparadise.side.fragments.fragmentHelper.baseDataContainer.BaseDataContainer;
import birdofparadise.side.interfaces.OnDashboardActivityToFragmentCommunication;
import birdofparadise.side.interfaces.OnFragmentToDashboardInteractionListener;


public class DashboardActivity extends AppCompatActivity implements ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener, OnFragmentToDashboardInteractionListener {


    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ExpandableListView drawerList;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private LinearLayout navHeader;
    private FrameLayout fragmentContainer;
    private AppCompatTextView userName, userOrganization, userBusinessRole, userANID;
    private Context context;
    private String newurl;


    private AppCompatCheckedTextView toolBarTitle;
    private Handler handler;

    private View.OnClickListener toolBarTitleOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AppCompatCheckedTextView appCompatCheckedTextView = ((AppCompatCheckedTextView) v);
            switch (comingFrom) {
                case Dashboard:
                    onDashboardActivityToFragmentCommunication.toolbarTitleClicked(appCompatCheckedTextView);
                    toolBarRightImageAdjacentTitle.setImageResource(R.drawable.collapse_menu);
                    break;
                case UserProfile:
                    break;
            }
        }
    };
    private AppCompatImageView toolBarRightImageAdjacentTitle;
    private OnDashboardActivityToFragmentCommunication onDashboardActivityToFragmentCommunication;
    private FragmentMessageContainer defaultFragmentMessageContainer;
    private AppCompatImageView toolbar_1st_icon;
    private AppCompatImageView toolbar_2st_icon;
    private View.OnClickListener toolbar_1st_iconOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onDashboardActivityToFragmentCommunication.toolbar1stIconClick();
        }
    };

    private View.OnClickListener toolbar_2st_iconOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onDashboardActivityToFragmentCommunication.toolbar2stIconClick();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        MessageBoxService.sharedInstance().setContext(this);
        ColorUtils.getInstance().setContext(getApplicationContext());

        setContentView(R.layout.activity_dashboard);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        ConfigurationsService.sharedInstance().setDisplaySize(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        LocaleUtils.sharedInstance().setLocale(getBaseContext());

        setSupportActionBar(mToolbar);
        initializeViews();

        toolBarTitle.setOnClickListener(toolBarTitleOnClickListener);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        setSupportActionBar(mToolbar);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        initDrawer();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);


        if (savedInstanceState == null) {
            addDefaultFragmentInPlace();
        } else {
            fragmentMessageContainerList = savedInstanceState.getParcelableArrayList(GlobalConstant.getInstance().getFragmentMessageConatinerKey());
            LoggingService.getInstance().log(getClass(), "Total fragment Relieved:" + fragmentMessageContainerList.size());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(GlobalConstant.getInstance().getFragmentMessageConatinerKey(), fragmentMessageContainerList);
//        LoggingService.getInstance().log(getClass(), "Total fragment Sent:" + fragmentMessageContainerList.size());
    }

    private void initDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerList = (ExpandableListView) findViewById(R.id.list_slidermenu);

        prepareListData();

        drawerList.setAdapter(new NavigationViewAdapter(this, listDataHeader, listDataChild));

        drawerList.setOnChildClickListener(this);
        drawerList.setOnGroupClickListener(this);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mToolbar.setTranslationX(slideOffset * drawerView.getWidth());

                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }


    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();


        Resources res = getResources();
        String[] headers = res.getStringArray(R.array.nav_drawer_labels);
        listDataHeader = Arrays.asList(headers);

        List<String> home, orders, invoices, otp, settings, custSupport, legal, sendAppLogs, signOut;
        String[] shome, sorders, sinvoices, sotp, ssettings, scustSupport, slegal, ssendAppLogs, ssignOut;

        shome = res.getStringArray(R.array.elements_home);
        home = Arrays.asList(shome);

        sorders = res.getStringArray(R.array.elements_orders);
        orders = Arrays.asList(sorders);

        sinvoices = res.getStringArray(R.array.elements_invoices);
        invoices = Arrays.asList(sinvoices);

        sotp = res.getStringArray(R.array.elements_otp);
        otp = Arrays.asList(sotp);

        ssettings = res.getStringArray(R.array.elements_settings);
        settings = Arrays.asList(ssettings);

        scustSupport = res.getStringArray(R.array.elements_custSupport);
        custSupport = Arrays.asList(scustSupport);

        slegal = res.getStringArray(R.array.elements_legal);
        legal = Arrays.asList(slegal);

        ssendAppLogs = res.getStringArray(R.array.elements_sendAppLogs);
        sendAppLogs = Arrays.asList(ssendAppLogs);

        ssignOut = res.getStringArray(R.array.elements_signOut);
        signOut = Arrays.asList(ssignOut);

        listDataChild.put(listDataHeader.get(0), home);
        listDataChild.put(listDataHeader.get(1), orders);
        listDataChild.put(listDataHeader.get(2), invoices);
        listDataChild.put(listDataHeader.get(3), otp);
        listDataChild.put(listDataHeader.get(4), settings);
        listDataChild.put(listDataHeader.get(5), custSupport);
        listDataChild.put(listDataHeader.get(6), legal);
        listDataChild.put(listDataHeader.get(7), sendAppLogs);
        listDataChild.put(listDataHeader.get(8), signOut);

    }

    private void initializeViews() {
        navHeader = (LinearLayout) findViewById(R.id.nav_header_container);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        fragmentContainer = (FrameLayout) findViewById(R.id.content_dashboard_framelayout_fragmentcontainer);
        toolBarTitle = (AppCompatCheckedTextView) mToolbar.findViewById(R.id.actionbar_layout_textview);
        toolBarRightImageAdjacentTitle = (AppCompatImageView) mToolbar.findViewById(R.id.actionbar_layout_textview_right_adjacent);
        toolbar_1st_icon = (AppCompatImageView) mToolbar.findViewById(R.id.toolbar_1st_right_icon);
        toolbar_2st_icon = (AppCompatImageView) mToolbar.findViewById(R.id.toolbar_2st_right_icon);
        setValuesToToolbarIcon(toolbar_1st_icon, toolbar_1st_iconOnClickListener, View.GONE);
        setValuesToToolbarIcon(toolbar_2st_icon, toolbar_2st_iconOnClickListener, View.GONE);
        userName = (AppCompatTextView) findViewById(R.id.userNameInNavigation);
        userOrganization = (AppCompatTextView) findViewById(R.id.userOrgInNavigation);
        userBusinessRole = (AppCompatTextView) findViewById(R.id.userAccTypeInNavigation);
        userANID = (AppCompatTextView) findViewById(R.id.userAnIDInNavigation);
    }

    private void setValuesToToolbarIcon(AppCompatImageView appCompatImageView, View.OnClickListener onClickListener, int i) {
        appCompatImageView.setOnClickListener(onClickListener);
        appCompatImageView.setVisibility(i);
    }


    private void addDefaultFragmentInPlace() {
        defaultFragmentMessageContainer = new FragmentMessageContainer(
                FragmentEnum.Dashboard,
                FragmentEnum.Dashboard,
                null,
                false,
                null
        );
        Bundle bundle = new Bundle();
        bundle.putParcelable(GlobalConstant.getInstance().getFragmentMessageConatinerKey(), defaultFragmentMessageContainer);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        DashboardFragment dashboardFragment = new DashboardFragment();
        dashboardFragment.setArguments(bundle);
        fragmentTransaction.add(fragmentContainer.getId(), dashboardFragment, FragmentEnum.Dashboard.toString());
        fragmentTransaction.addToBackStack(FragmentEnum.Dashboard.toString());
        fragmentTransaction.commit();
        fragmentMessageContainerList.add(defaultFragmentMessageContainer);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (onDashboardActivityToFragmentCommunication.shouldDoNormalOperationOnBackPressed()) {
                int currentFragment = fragmentMessageContainerList.size() - 1;
                if (currentFragment > 0) {
                    FragmentMessageContainer fragmentMessageContainer = fragmentMessageContainerList.get(currentFragment - 1);
                    BaseDataContainer baseDataContainer = onDashboardActivityToFragmentCommunication.getComingData();
                    if (baseDataContainer != null) {
                        fragmentMessageContainer.setComeback(true);
                        fragmentMessageContainer.setComingData(baseDataContainer);
                    }
                    fragmentMessageContainerList.remove(currentFragment);
                    setFragmentBack(fragmentMessageContainer);
                } else {
                    finish();
                }
            }
        }
    }

    private void setFragmentBack(FragmentMessageContainer fragmentMessageContainer) {
        switch (fragmentMessageContainer.getGoingTo()) {
            case OneTimePassword:
            case Dashboard:
                setToolbarDefaultSetting();
                break;
        }
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        BaseFragment baseFragment = (BaseFragment) getFragmentManager().findFragmentByTag(fragmentMessageContainer.getGoingTo().toString());
        if (baseFragment != null) {
            Bundle bundle = baseFragment.getArguments();
            if (bundle != null) {
                bundle.putParcelable(GlobalConstant.getInstance().getFragmentMessageConatinerKey(), fragmentMessageContainer);
            } else {
                bundle = new Bundle();
                bundle.putParcelable(GlobalConstant.getInstance().getFragmentMessageConatinerKey(), fragmentMessageContainer);
                baseFragment.setArguments(bundle);
            }
        }
        fragmentTransaction.replace(fragmentContainer.getId(), baseFragment, fragmentMessageContainer.getGoingTo().toString());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    FragmentEnum comingFrom;

    @Override
    public void setToolbarTitle(final String s, final FragmentEnum comingFromm) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                comingFrom = comingFromm;
                toolBarTitle.setOnClickListener(null);
                toolBarTitle.setClickable(false);
                switch (comingFromm) {
                    case Dashboard:
                        toolBarTitle.setText(s);
                        toolBarTitle.setClickable(true);
                        toolBarTitle.setChecked(true);
                        toolBarTitle.setOnClickListener(toolBarTitleOnClickListener);
                        toolBarRightImageAdjacentTitle.setVisibility(View.VISIBLE);
                        break;
                    case UserProfile:
                    case BusinessRoleSelectionFragment:
                    case OrderList:
                    case InvoiceDetail:
                    case Settings:
                    case MyCustomers:
                    case PushNotificationsFragment:
                    case OneTimePassword:
                    case OrderDetailItemDescription:
                    case OrderDetailItemShipFragment:
                        toolBarTitle.setText(s);
                        break;
                }
            }
        });
    }

    @Override
    public void setOnDashboardActivityToFragmentCommunication(OnDashboardActivityToFragmentCommunication onDashboardActivityToFragmentCommunication) {
        this.onDashboardActivityToFragmentCommunication = onDashboardActivityToFragmentCommunication;
    }

    @Override
    public void closeCustomerMenu() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                LoggingService.getInstance().log(getClass(), "closeCustomerMenu");
                toolBarTitle.setChecked(true);
                toolBarRightImageAdjacentTitle.setImageResource(R.drawable.expand_arrow);
            }
        });
    }

    @Override
    public void openDesiredFragment(FragmentMessageContainer fragmentMessageContainer) {
        setToolbarDefaultSetting();
        Bundle bundle = new Bundle();
        bundle.putParcelable(GlobalConstant.getInstance().getFragmentMessageConatinerKey(), fragmentMessageContainer);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        BaseFragment baseFragment = (BaseFragment) getFragmentManager().findFragmentByTag(fragmentMessageContainer.getGoingTo().toString());
        if (baseFragment == null) {
            switch (fragmentMessageContainer.getGoingTo()) {
                case Dashboard:
                    break;
                case OneTimePassword:
                    baseFragment = new OneTimePasswordFragment();
                    break;

            }
            if (baseFragment != null) {
                baseFragment.setArguments(bundle);
                fragmentTransaction.replace(fragmentContainer.getId(), baseFragment, fragmentMessageContainer.getGoingTo().toString());
            }
        } else {
            baseFragment.getArguments().putParcelable(GlobalConstant.getInstance().getFragmentMessageConatinerKey(), fragmentMessageContainer);
            fragmentTransaction.replace(fragmentContainer.getId(), baseFragment, fragmentMessageContainer.getGoingTo().toString());
        }
        toolBarRightImageAdjacentTitle.setVisibility(View.GONE);
        fragmentTransaction.addToBackStack(fragmentMessageContainer.getGoingTo().toString());
        maintainFragmentStack(fragmentMessageContainer);
        fragmentTransaction.commit();
    }

    private void setToolbarDefaultSetting() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mToolbar.setVisibility(View.VISIBLE);
                toolbar_1st_icon.setVisibility(View.GONE);
                toolbar_2st_icon.setVisibility(View.GONE);
            }
        });
    }

    ArrayList<FragmentMessageContainer> fragmentMessageContainerList = new ArrayList<>();

    private void maintainFragmentStack(FragmentMessageContainer fragmentMessageContainer) {
        switch (fragmentMessageContainer.getGoingTo()) {
            case Dashboard:
                break;
            case OneTimePassword:
                break;
        }
    }


    @Override
    public void setToolbarInvisible() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mToolbar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void backButtonClicked() {
        handler.post(new Runnable() {
            public void run() {
                onBackPressed();
            }
        });
    }

    @Override
    public void setToolbar1stRight(int icon) {
        toolbar_1st_icon.setVisibility(View.VISIBLE);
        toolbar_1st_icon.setImageResource(icon);
    }

    @Override
    public void setToolbar2ndRight(int icon) {
        toolbar_2st_icon.setVisibility(View.VISIBLE);
        toolbar_2st_icon.setImageResource(icon);
    }

    @Override
    public void setToolbarVisible() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mToolbar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        Bundle mBundle = new Bundle();
        mDrawerLayout.closeDrawer(GravityCompat.START);
        drawerList.collapseGroup(groupPosition);
        switch (childPosition) {

            case 0:

                break;

            case 1:

                break;

            case 2:

                break;

        }
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (groupPosition) {
            case 0:
                hamburgerRedirection(FragmentEnum.Dashboard, null);
                break;

            case 3:
                hamburgerRedirection(FragmentEnum.OneTimePassword, new FragmentMessageContainer(
                        FragmentEnum.OneTimePassword,
                        FragmentEnum.Dashboard,
                        new StringContainer(getString(R.string.one_time_password)),
                        false,
                        null
                ));
                break;

            case 6:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case 7:
                LoggingService.getInstance().SendLoagcatMail(this);
                break;
            case 8:
                DashboardActivity.this.finish();
                break;

        }

        return false;
    }

    private void hamburgerRedirection(FragmentEnum anEnum, FragmentMessageContainer fragmentMessageContainer) {
        fragmentMessageContainerList.clear();
        switch (anEnum) {
            case Dashboard:
                addDefaultFragmentInPlace();
                break;
            case OneTimePassword:
                fragmentMessageContainerList.add(defaultFragmentMessageContainer);
                openDesiredFragment(fragmentMessageContainer);
                break;
        }

    }

}