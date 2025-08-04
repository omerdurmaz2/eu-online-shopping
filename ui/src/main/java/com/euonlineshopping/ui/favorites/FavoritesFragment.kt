import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.euonlineshopping.ui.databinding.FragmentFavoritesBinding
import com.euonlineshopping.ui.databinding.FragmentHomeBinding
import com.euonlineshopping.ui.favorites.FavoritesViewModel
import com.euonlineshopping.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val viewModel: FavoritesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}