function GalleryItems(deleteMessage, deleteConfirmMessage, deleteGalleryItemUrl) {

    var instance = this;

    instance.appendDeleteItem = function appendDeleteitem(jqueryElem) {
        jqueryElem.hover(function () {
            $(this).find('.deleteItem').show();
        }, function () {
            $(this).find('.deleteItem').hide();
        });

        jqueryElem.each(function (index) {
            var itemWrapper = $(this);
            var deleteItem = itemWrapper.find('.deleteItem a');
            deleteItem.click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var deleteConfirm = confirm(deleteConfirmMessage);
                if (deleteConfirm) {
                    var url = $(this).attr('href');
                    $.ajax({
                        url:url,
                        type:'GET',
                        success:function (data) {
                            itemWrapper.remove();
                        }
                    });
                }
            });
        });
    };

    // Custom example logic
    function getElemById(id) {
        return document.getElementById(id);
    }

    var loader = null;

    var uploader2 = new plupload.Uploader({
        runtimes:'gears,html5,flash,silverlight,browserplus',
        browse_button:'pickfiles2',
        container:'container2',
        multipart:true,
        max_file_size:'10mb',
        multi_selection:false,
        url:'/admin/uploadGalleryItem',
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

    uploader2.bind('FilesAdded', function (up, files) {

        if (uploader2.files.length == 1) {
            removeFile(uploader2, up);
        }

        for (var i in files) {
            getElemById('filelist2').innerHTML += '<span id="' + files[i].id + '">' + files[i].name + ' (' + plupload.formatSize(files[i].size) + ') <b></b></span>';
        }

        $('#uploadfiles2').show();
    });

    uploader2.bind('FileUploaded', function (up, file, info) {
        var data = jQuery.parseJSON(info.response);
        var itemWrapper = $('<span class="itemWrapper"></span>');
        var deleteUrl = deleteGalleryItemUrl + '?gphotoId=' + data.gphotoId;
        itemWrapper.append('<span class="deleteItem"><a href="' + deleteUrl + '">' + deleteMessage + '</a></span>');
        itemWrapper.append('<img alt="..." src="' + data.thumbUrl + '">');
        instance.appendDeleteItem(itemWrapper);
        $('#galleryItems').append(itemWrapper);
        removeFile(uploader2, up);
        $('#uploadfiles2').hide();
        loader.remove();
    });


    uploader2.bind('UploadFile', function (up, file) {
        loader = new ajaxLoader($('body'), {classOveride:'blue-loader', bgColor:'#000', opacity:'0.3'});
    });

    uploader2.bind('UploadProgress', function (up, file) {
        var elemById = getElemById(file.id);
        if (elemById) {
            elemById.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
        }
    });

    getElemById('uploadfiles2').onclick = function () {
        uploader2.start();
        return false;
    };

    uploader2.init();

}