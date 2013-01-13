function MainImage() {

    // Custom example logic
    function getElemById(id) {
        return document.getElementById(id);
    }

    var loader = null;

    var uploader = new plupload.Uploader({
        runtimes:'gears,html5,flash,silverlight,browserplus',
        browse_button:'pickfiles',
        container:'container',
        multipart:true,
        max_file_size:'10mb',
        multi_selection:false,
        url:'/admin/uploadTest',
        resize:{width:800, height:600, quality:90},
        flash_swf_url:'/js/plupload/plupload.flash.swf',
        silverlight_xap_url:'/js/plupload/plupload.silverlight.xap',
        filters:[
            {title:"Image files", extensions:"jpg,gif,png"}
        ]
    });

    function removeFile(uploader, up) {
        var fileToRemove = uploader.files[0];
        var idToRemove = fileToRemove.id;
        uploader.removeFile(fileToRemove);
        $('#' + idToRemove).remove();
        up.refresh(); // Reposition Flash/Silverlight
    }

    uploader.bind('FilesAdded', function (up, files) {

        if (uploader.files.length == 1) {
            removeFile(uploader, up);
        }

        for (var i in files) {
            getElemById('filelist').innerHTML += '<span id="' + files[i].id + '">' + files[i].name + ' (' + plupload.formatSize(files[i].size) + ') <b></b></span>';
        }

        $('#uploadfiles').show();
    });

    uploader.bind('FileUploaded', function (up, file, info) {
        $('#mainUrlId').val(info.response);
        removeFile(uploader, up);
        $('#uploadfiles').hide();
        loader.remove();
    });


    uploader.bind('UploadFile', function (up, file) {
        loader = new ajaxLoader($('body'), {classOveride:'blue-loader', bgColor:'#000', opacity:'0.3'});
    });

    uploader.bind('UploadProgress', function (up, file) {
        var elemById = getElemById(file.id);
        if (elemById) {
            elemById.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
        }
    });

    getElemById('uploadfiles').onclick = function () {
        uploader.start();
        return false;
    };

    uploader.init();

}

