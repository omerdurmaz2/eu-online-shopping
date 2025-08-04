import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.euonlineshopping.ui.cart.CartViewModel
import com.euonlineshopping.ui.databinding.FragmentCartBinding
import com.euonlineshopping.ui.databinding.FragmentHomeBinding
import com.euonlineshopping.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {

    private val viewModel: CartViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = CartFragment()
    }
}