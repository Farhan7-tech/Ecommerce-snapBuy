package com.ecommerce.sbEcommerce.service;

import com.ecommerce.sbEcommerce.Exceptions.APIException;
import com.ecommerce.sbEcommerce.Exceptions.ResourseNotFoundException;
import com.ecommerce.sbEcommerce.Repository.CartRepository;
import com.ecommerce.sbEcommerce.Repository.CategoryRepository;
import com.ecommerce.sbEcommerce.Repository.ProductRepository;
import com.ecommerce.sbEcommerce.model.Cart;
import com.ecommerce.sbEcommerce.model.Category;
import com.ecommerce.sbEcommerce.model.Product;
import com.ecommerce.sbEcommerce.payload.CartDTO;
import com.ecommerce.sbEcommerce.payload.ProductDTO;
import com.ecommerce.sbEcommerce.payload.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @Value("${project.image}")
    private String path;

    @Value("${image.base.url}")
    private String imageBaseUrl;


    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourseNotFoundException("Category", "CategoryId", categoryId));
        //check if product exist or not
        boolean isProductNotPresent = true;
        List<Product> products = category.getProducts();
        for (Product value : products) {
            if (value.getProductName().equals(productDTO.getProductName())) {
                isProductNotPresent = false;
                break;
            }
        }
        if (isProductNotPresent) {
            Product product = modelMapper.map(productDTO, Product.class);
            product.setImage("default.png");
            product.setCategory(category);
            double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
            product.setSpecialPrice(specialPrice);
            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
        } else {
            throw new APIException("Product already exists");
        }
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String keyword, String category) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Specification<Product> spec = Specification.where(null);

        if(keyword != null && !keyword.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("productName")), "%"+keyword.toLowerCase()+"%"));
        }

        if(category != null && !category.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("category").get("categoryName"), category));
        }

        Page<Product> ProductPage = productRepository.findAll(spec , pageDetails);

        List<Product> products = ProductPage.getContent();
        List<ProductDTO> productDTOS = products.stream().map(product -> {
                ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
                productDTO.setImage(constructImageUrl(product.getImage()));
                return productDTO;
        }).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(ProductPage.getNumber());
        productResponse.setPageSize(ProductPage.getSize());
        productResponse.setTotalElements(ProductPage.getTotalElements());
        productResponse.setTotalPages(ProductPage.getTotalPages());
        productResponse.setLastPage(ProductPage.isLast());
        return productResponse;

    }

    private String constructImageUrl(String imageName){
        return imageBaseUrl.endsWith("/") ? imageBaseUrl + imageName : imageBaseUrl + "/" + imageName;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourseNotFoundException("Category", "CategoryId", categoryId));
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> ProductPage = productRepository.findByCategoryOrderByPriceAsc(category, pageDetails);
        List<Product> products = ProductPage.getContent();
        List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        if (products.isEmpty()) throw new APIException(category.getCategoryName() + " does not have any products");
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(ProductPage.getNumber());
        productResponse.setPageSize(ProductPage.getSize());
        productResponse.setTotalElements(ProductPage.getTotalElements());
        productResponse.setTotalPages(ProductPage.getTotalPages());
        productResponse.setLastPage(ProductPage.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> ProductPage = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%', pageDetails);
        List<Product> products = ProductPage.getContent();
        List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        if (products.isEmpty()) throw new APIException("No products found with keyword " + keyword);
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(ProductPage.getNumber());
        productResponse.setPageSize(ProductPage.getSize());
        productResponse.setTotalElements(ProductPage.getTotalElements());
        productResponse.setTotalPages(ProductPage.getTotalPages());
        productResponse.setLastPage(ProductPage.isLast());
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        //get the existing product
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourseNotFoundException("Product", "ProductId", productId));
        //update the product info with the one in request body
        Product product = modelMapper.map(productDTO, Product.class);
        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDiscount(product.getDiscount());
        existingProduct.setSpecialPrice(product.getSpecialPrice());
        Product savedProduct = productRepository.save(existingProduct);

        List<Cart> carts = cartRepository.findCartsByProductId(productId);

        List<CartDTO> cartDTOs = carts.stream().map(cart -> {
            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

            List<ProductDTO> products = cart.getCartItems().stream()
                    .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class)).collect(Collectors.toList());

            cartDTO.setProducts(products);

            return cartDTO;

        }).collect(Collectors.toList());

        cartDTOs.forEach(cart -> cartService.updateProductInCarts(cart.getCartId(), productId));

        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product productToBeDeleted = productRepository.findById(productId)
                .orElseThrow(() -> new ResourseNotFoundException("Product", "ProductId", productId));
        List<Cart> carts = cartRepository.findCartsByProductId(productId);
        carts.forEach(cart -> cartService.deleteProductFromCart(cart.getCartId(), productId));
        productRepository.delete(productToBeDeleted);
        return modelMapper.map(productToBeDeleted, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        //get the product from DB which is to be updated with image
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourseNotFoundException("Product", "ProductId", productId));
        //upload image to server
        //get the file name of uploaded image

        String filename = fileService.uploadImage(path, image);
        //update the new file name to the product
        existingProduct.setImage(filename);
        //Save updated product into DB
        Product updatedProduct = productRepository.save(existingProduct);
        //return DTO
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }


}
