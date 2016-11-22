package org.cpc.yaounde.eservice.fragment.om;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.cpc.yaounde.eservice.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class OMPaymentFragment extends Fragment {


    public static OMPaymentFragment newInstance(String param1, String param2) {
        OMPaymentFragment fragment = new OMPaymentFragment();

        return fragment;
    }

    public OMPaymentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ompayment_payment, container, false);
    }
}
