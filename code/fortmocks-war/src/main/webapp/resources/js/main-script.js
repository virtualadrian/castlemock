
$(document).ready(function(){
    $('.question + .answer').hide();
    $('.question').on('click', function(){
        $(this).next('.answer').slideToggle('fast');
   });
});