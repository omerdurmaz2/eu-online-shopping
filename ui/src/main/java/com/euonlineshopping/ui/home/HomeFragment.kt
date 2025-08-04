import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.euonlineshopping.ui.databinding.FragmentHomeBinding
import com.euonlineshopping.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProducts()
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}