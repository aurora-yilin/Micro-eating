package com.lyl.controller;

import com.lyl.common.ResultType;
import com.lyl.enums.CommonEnum;
import com.lyl.exception.MyIOException;
import com.lyl.service.PicturesService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * PictureController
 *
 * @author lyl
 * @date 2020/11/16 18:01
 * @since 1.0.0
 **/
@RestController
@RequestMapping("/picture")
public class PictureController {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(PictureController.class);
    
    @DubboReference
    private PicturesService picturesService;

    /**
     * 根据url地址来获取相关的图片
     * @param pictureName
     * @param response
     * @throws IOException
     */
    @GetMapping("/getPicture/{pictureName}")
    public void getPicture(@PathVariable("pictureName") String pictureName, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = null;
        try {
//        读取图片
            File picture = picturesService.getPicture(pictureName);
            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(picture));
            response.setContentType("image/png");
            outputStream = response.getOutputStream();
            if (bufferedImage != null) {
                ImageIO.write(bufferedImage, "png", outputStream);
                LOGGER.info("获取图片{}",pictureName);
            }
        } catch (IOException e) {
            LOGGER.error("获取图片异常{}",e.getMessage());
            throw new MyIOException(CommonEnum.SERVERERROR.getCode(),"IO Exception");
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                }catch (Exception e){
                    throw new MyIOException(CommonEnum.SERVERERROR.getCode(),"IO Exception");
                }
            }
        }
    }

    /**
     * 上传图片
     * @param multipartFile
     * @return
     */
    @PostMapping("/admin/saveImage")
    public ResultType saveimage(@RequestParam("file") MultipartFile multipartFile){
        if (multipartFile.isEmpty()) {
            return ResultType.CLIENTERROR(CommonEnum.CLIENTERROR.getCode(),CommonEnum.CLIENTERROR.getMsg(),null);
        }else{
            //获取上传的文件的名字
            String fileName = multipartFile.getOriginalFilename();
            //截取上传文件的后缀名
            String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
            //通过UUID随机生成新的文件名，并拼接上传文件的原有后缀名
            String randomFilename = UUID.randomUUID().toString()
                    .replace("-", "") + "." + prefix;

            //从商品模块获取到文件路径对象
            File filelocations = picturesService.uploadPicture();
            //判断文件路径是否存在，不存在则创建路径
            if (!filelocations.exists()) {
                filelocations.mkdirs();
            }

            File image = new File(filelocations, randomFilename);

            //防止随机生成的文件名完全相同
//            while (image.exists()){
//                randomFilename = UUID.randomUUID().toString()
//                        .replace("-", "") + "." + prefix;
//                image = new File(filelocations,randomFilename);
//            }
            LOGGER.info("image path:"+image);
            try{
                multipartFile.transferTo(image);
            }catch (IOException e) {
                e.printStackTrace();
                return ResultType.SERVERERROR(CommonEnum.SERVERERROR.getCode(),CommonEnum.SERVERERROR.getMsg(),null);
            }

            return ResultType.SERVERERROR(CommonEnum.SUCCESS.getCode(),CommonEnum.SUCCESS.getMsg(),randomFilename);
        }
    }
}
