package com.subha.optymize.presentation.product_details

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.subha.optymize.R
import com.subha.optymize.core.helper.Resource
import com.subha.optymize.core.util.Endpoints
import com.subha.optymize.databinding.FragmentProductDetailsBinding
import com.subha.optymize.viewModel.ShoeaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_details.*
import me.relex.circleindicator.CircleIndicator

@AndroidEntryPoint
class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {

    private var _binding: FragmentProductDetailsBinding? = null
    val binding: FragmentProductDetailsBinding?
        get() = _binding


    private val viewModel: ShoeaViewModel by viewModels()
    private val args: ProductDetailsFragmentArgs by navArgs()

    lateinit var viewPagerAdapter: ImageSlideAdapter
    lateinit var indicator: CircleIndicator
    private var minQuant: Int = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductDetailsBinding.bind(view)

        setUI()
        apiCall(args.id)
    }

    private fun apiCall(id: String) {
        viewModel.productList(Endpoints.PRODUCTS + "/$id")
        viewModel.productListLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {

                    val totalCoupons = it.data!!.stock
                    val price = it.data!!.price

                    imageSlider(it.data!!.images)
                    binding!!.tvTitle.text = it.data.title
                    binding!!.tvRating.text = it.data.rating.toString()
                    binding!!.tvDescription.text = it.data.description
                    priceUpdate(it.data.price, it.data.stock)

                    binding!!.ivQuantityMore.setOnClickListener {
                        if (minQuant > totalCoupons - 1) {
                        } else {
                            minQuant++
                        }
                        binding!!.tvOrderQuantity.text = minQuant.toString()
                        if (minQuant <= 1) {
                            binding!!.ivQuantityLess.setColorFilter(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.faded_black
                                )
                            )
                            binding!!.ivQuantityMore.setColorFilter(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                        } else if (minQuant >= totalCoupons) {
                            binding!!.ivQuantityMore.setColorFilter(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.faded_black
                                )
                            )
                            binding!!.ivQuantityLess.setColorFilter(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                        } else {
                            binding!!.ivQuantityLess.setColorFilter(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                            binding!!.ivQuantityMore.setColorFilter(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                        }
                        binding!!.tvPrice.text = "$ " + minQuant * price

                    }

                    binding!!.ivQuantityLess.setOnClickListener {
                        if (minQuant <= 1) {
                        } else {
                            minQuant--
                        }
                        binding!!.tvOrderQuantity.text = minQuant.toString()
                        if (minQuant <= 1) {
                            binding!!.ivQuantityLess.setColorFilter(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.faded_black
                                )
                            )
                            binding!!.ivQuantityMore.setColorFilter(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                        } else if (minQuant >= totalCoupons) {
                            binding!!.ivQuantityMore.setColorFilter(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.faded_black
                                )
                            )
                            binding!!.ivQuantityLess.setColorFilter(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                        } else {
                            binding!!.ivQuantityLess.setColorFilter(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                            binding!!.ivQuantityMore.setColorFilter(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                        }
                        binding!!.tvPrice.text = "$ " + minQuant * price
                    }

                    binding!!.llShimmer.stopShimmerAnimation()
                    binding!!.llShimmer.visibility = View.GONE
                    binding!!.llDetailsPage.visibility = View.VISIBLE

                }
                is Resource.Error -> {
                    val snack = Snackbar.make(
                        binding!!.root,
                        it.message.toString(),
                        Snackbar.LENGTH_INDEFINITE
                    )
                    snack.setAction("DISMISS", View.OnClickListener {
                        snack.dismiss()
                    })
                    snack.show()
                }
                is Resource.Loading -> {
                    binding!!.llShimmer.startShimmerAnimation()
                    binding!!.llShimmer.visibility = View.VISIBLE
                    binding!!.llDetailsPage.visibility = View.GONE
                }
            }
        })
    }

    private fun priceUpdate(price: Int, stock: Int) {

        var minQuant: Int = 1
        val maxQuant: Int = stock
        binding!!.tvOrderQuantity.text = minQuant.toString()
        binding!!.tvPrice.text = "$ " + minQuant * price

        if (minQuant <= 1) {
            binding!!.ivQuantityLess.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.faded_black
                )
            )
            binding!!.ivQuantityMore.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
        } else if (minQuant >= maxQuant) {
            binding!!.ivQuantityMore.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.faded_black
                )
            )
            binding!!.ivQuantityLess.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
        } else {
            binding!!.ivQuantityLess.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            binding!!.ivQuantityMore.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
        }
    }

    private fun imageSlider(images: List<String>) {
        viewPagerAdapter = ImageSlideAdapter(requireContext(), images)
        binding!!.viewpager.adapter = viewPagerAdapter
        indicator = requireView().findViewById(R.id.indicator) as CircleIndicator
        indicator.setViewPager(viewpager)
    }

    private fun setUI() {

        binding!!.llShimmer.startShimmerAnimation()
        binding!!.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_productDetailsFragment_to_productListFragment)
        }

    }


    override fun setUserVisibleHint(visible: Boolean) {
        super.setUserVisibleHint(visible)
        if (visible && isResumed) {
            setUI()
            apiCall(args.id)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}