package hiccup.hiccupstore.product.controller;

import hiccup.hiccupstore.commonutil.aws.awsS3Service;
import hiccup.hiccupstore.commonutil.file.FileStore;
import hiccup.hiccupstore.commonutil.file.UploadFile;
import hiccup.hiccupstore.product.dto.Product;
import hiccup.hiccupstore.product.dto.ProductAddForm;
import hiccup.hiccupstore.product.dto.ProductImage;
import hiccup.hiccupstore.product.dto.page.Page;
import hiccup.hiccupstore.product.service.ProductService;
//import hiccup.hiccupstore.product.util.FileStore;
import hiccup.hiccupstore.product.util.ImageType;
//import hiccup.hiccupstore.product.util.UploadFile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductManageController {
    private final ProductService productService;
    private final hiccup.hiccupstore.commonutil.aws.awsS3Service awsS3Service;

    @GetMapping("/product/write")
    public String productWrite(){
        return "product/productWrite";
    }

    @PostMapping("/product/write")
    public String productWrite(@ModelAttribute ProductAddForm productAddForm,
                             @RequestParam("productImageFile")List<MultipartFile> productImage,
                             @RequestParam("detailImageFile")List<MultipartFile> detailImage) throws IOException
    {
        List<UploadFile> uploadProductFile = awsS3Service.uploadImage("product", productImage);
        ProductImage uploadProductImage = ProductImage.builder().imageName(uploadProductFile.get(0).getStoreFileName())
                        .imagePath(awsS3Service.getFullPath("product",uploadProductFile.get(0).getStoreFileName()))
                        .imageType(ImageType.PRODUCT.getValue()) .build();

        List<UploadFile> uploadDetailFile = awsS3Service.uploadImage("product",detailImage);
        ProductImage uploadDetailImage = ProductImage.builder().imageName(uploadDetailFile.get(0).getStoreFileName())
                        .imagePath(awsS3Service.getFullPath("product",uploadDetailFile.get(0).getStoreFileName()))
                        .imageType(ImageType.DETAIL.getValue()) .build();

        productService.addProduct(productAddForm,uploadProductImage,uploadDetailImage);
        return "redirect:/product/detail?pid="+uploadProductImage.getProductId();

    }

    @GetMapping("/product/edit")
    public String productEdit(Model model, @RequestParam("productId") Integer productId){
        model.addAttribute("product", productService.getProductById(productId));
        ArrayList<ProductImage> productImages =productService.getProductImageListById(productId);

        model.addAttribute("productImageName",productImages.stream().
                filter(Image -> Image.getImageType().equals(ImageType.PRODUCT.getValue())).
                findFirst().orElse(new ProductImage()).getImageName());
        model.addAttribute("detailImageName",productImages.stream().
                filter(Image -> Image.getImageType().equals(ImageType.DETAIL.getValue())).
                findFirst().orElse(new ProductImage()).getImageName());

        return ("product/productEdit");
    }

    @PostMapping("/product/edit")
    public String productEdit(@ModelAttribute Product product,
                              @RequestParam(value = "productImageFile",required = false)List<MultipartFile> productImage,
                              @RequestParam(value = "detailImageFile", required = false)List<MultipartFile> detailImage,
                              @RequestParam(value = "PreProductImage",required = false)String preProductImage,
                              @RequestParam(value = "PreDetailImage", required = false)String preDetailImage) throws IOException
    {
        ArrayList<ProductImage> productImages =productService.getProductImageListById(product.getProductId());

        if(null == preProductImage && ""!=productImage.get(0).getOriginalFilename()){

            List<UploadFile> uploadProductFile = awsS3Service.uploadImage("product",productImage);

            ProductImage uploadProductImage = ProductImage.builder().productId(product.getProductId())
                    .imageName(uploadProductFile.get(0).getStoreFileName())
                    .imagePath(awsS3Service.getFullPath("product",uploadProductFile.get(0).getStoreFileName()))
                    .imageType(ImageType.PRODUCT.getValue()) .build();
            productService.editProductImage(uploadProductImage);

            awsS3Service.deleteFile("product",productImages.stream().
                    filter(Image -> Image.getImageType().equals(ImageType.PRODUCT.getValue())).
                    findFirst().orElse(new ProductImage()).getImagePath());

        }

        if(null == preDetailImage && ""!=detailImage.get(0).getOriginalFilename()){

            List<UploadFile> uploadDetailFile = awsS3Service.uploadImage("product",detailImage);

            ProductImage uploadDetailImage = ProductImage.builder().productId(product.getProductId())
                    .imageName(uploadDetailFile.get(0).getStoreFileName())
                    .imagePath(awsS3Service.getFullPath("product",uploadDetailFile.get(0).getStoreFileName()))
                    .imageType(ImageType.DETAIL.getValue()) .build();
            productService.editProductImage(uploadDetailImage);

            awsS3Service.deleteFile("product",productImages.stream().
                    filter(Image -> Image.getImageType().equals(ImageType.DETAIL.getValue())).
                    findFirst().orElse(new ProductImage()).getImagePath());
        }

        productService.editProduct(product);

        return "redirect:/product/detail?pid="+product.getProductId();
    }
    @GetMapping("/product/delete")
    public String deleteProduct(@RequestParam(value = "productId")Integer productId){
        productService.delProduct(productId);
        return "redirect:/product/productlist/liquor";
    }

}
