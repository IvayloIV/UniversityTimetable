$(window).on('load', () => {
    $('.status-update').on('change', updateStatus);
    $('.course-update').on('change', updateStatus);
    $('.delete-course').on('click', deleteCourse);
    $('.subject-details').on('click', showSubjectDetails);

    $('.course-upper-part').each((i, e) => {
        if ($(e).find('.delete-course').length == 0) {
            $(e).removeClass('pt-2');
            $(e).addClass('pt-3');
        }
    });

    function updateStatus(e) {
        var form = $(e.target).parent();
        var url = form.attr('action');
        var csrf = form.children().first().val();

        $.ajax({
            type: 'PATCH',
            url: url,
            headers: {
                'X-CSRF-Token': csrf
            },
            success: function(msg) {
                $('.toast-body').text(msg);
                $('.toast').toast('show');
            }
        });
    }

    function deleteCourse(e) {
        var target = $(e.target);
        var form = target.closest('.form');
        var url = form.attr('action');
        var csrf = form.children().first().val();
        var courseDetails = target.closest('.course-details');

        $.ajax({
            type: 'DELETE',
            url: url,
            headers: {
                'X-CSRF-Token': csrf
            },
            success: function(msg) {
                $('.toast-body').text(msg);
                $('.toast').toast('show');
                form.closest('.course').remove();
                if (courseDetails.find('.course').length == 0) {
                    courseDetails.find('.empty-subject').removeClass('d-none');
                }
            }
        });
    }

    function showSubjectDetails(e) {
        $(e.target).blur();
        var courseId = $(e.target).parent().find('.course-id').val();
        $('.course-details').each((i, e) => $(e).addClass('d-none'));
        $('#course-' + courseId).parent().removeClass('d-none');
        $('.subject-card').css('width', '260px');
        $('.subject-title').css('margin-right', '2rem')
        $('.subject-title-hr').css('margin-right', '2rem')
        $('.subjects-col').addClass('pe-0')
    }
});