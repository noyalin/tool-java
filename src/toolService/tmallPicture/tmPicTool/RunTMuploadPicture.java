package toolService.tmallPicture.tmPicTool;

import toolService.tmallPicture.tmPicTool.service.TmallPictureService;

/**
 * Created by simin on 2017/12/14.
 */
public class RunTMuploadPicture {
    public static void main(String[] args) {
        TmallPictureService tmallPictureService =new TmallPictureService();
        tmallPictureService.run();
    }
}
