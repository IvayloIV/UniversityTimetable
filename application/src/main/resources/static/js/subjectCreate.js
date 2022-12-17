$(window).on('load', () => {
    $('.create-specialty').on('click', e => {
        e.preventDefault();
        removeSelectDisabled($('.degree-select'), 1);
        removeSelectDisabled($('.mode-select'), 1);
        removeSelectDisabled($('.faculty-select'), 1);
        removeSelectDisabled($('.faculty-select-disabled'), 0);
        removeSelectDisabled( $('.week-select'), 1);
        removeSelectDisabled($('.department-select'), 1);
        removeSelectDisabled($('.department-select-disabled'), 0);
        removeSelectDisabled($('.specialty-select'), 1);
        removeSelectDisabled($('.specialty-select-disabled'), 0);
        removeSelectDisabled($('.year-select'), 1);
        removeSelectDisabled($('.room-select'), 1);
        removeSelectDisabled($('.teacher-select'), 1);
        removeSelectDisabled($('.course-time-day'), 1);
        $('#subject-create-form').submit();
    });

    function removeSelectDisabled(selectItems, endIndex) {
        for (var i = 0; i < selectItems.length - endIndex; i++) {
            var item = selectItems[i];
            $(item).css('background-color', '#e9ecef')
            $(item).removeAttr('disabled');
        }
    }
});