$(window).on('load', () => {
    var padNumberLeft = nr => `${nr}`.padStart(2, '0');
    var facultySelect = $('.faculty-select');
    var departmentIdInput = $('#department-id');
    var departmentSelect = $('.department-select');
    var deleteButtonText = $('.delete-button-text');
    var specialtyListWrapper = $('.specialty-list-wrapper');
    var specialtyCreateForm = $('#specialty-create-form');

    if (facultySelect.children().length > 0) {
        loadDepartments();
    }

    facultySelect.on('change', loadDepartments);
    departmentSelect.on('change', loadSpecialties);

    function loadDepartments() {
        var facultyId = facultySelect.find(":selected").val();

        $.get(`/department/faculty/${facultyId}`)
        .then((res) => {
                departmentSelect.empty();
                res.forEach(d => departmentSelect.append(
                    $('<option>').val(d.id).text(d.nameBg)
                        .attr("selected", departmentIdInput.val() == d.id)
                ));
                departmentIdInput.val('');
                loadSpecialties();
            }
        );
    }

    function loadSpecialties() {
        var facultyId = facultySelect.find(":selected").val();
        var departmentId = departmentSelect.find(":selected").val();

        $.get(`/specialty/faculty/${facultyId}/department/${departmentId}`)
            .then((res) => {
                    $('.specialty-row').remove();

                    if (res.length == 0) {
                        specialtyCreateForm.css('margin-bottom', "21.3rem");
                        specialtyListWrapper.addClass('d-none');
                    } else {
                        specialtyCreateForm.css('margin-bottom', "0");
                        specialtyListWrapper.removeClass('d-none');
                        var csrf = $("#specialty-create-form input[name='_csrf']").val();
                        res.forEach(s => {
                            var createdDate = new Date(s.createdDate);
                            var dformat = [padNumberLeft(createdDate.getDate()),
                                            padNumberLeft(createdDate.getMonth() + 1),
                                            createdDate.getFullYear()].join('.') + ' ' +
                                          [padNumberLeft(createdDate.getHours()),
                                           padNumberLeft(createdDate.getMinutes())].join(':');

                            $('.specialties-body').append($('<tr>').addClass('specialty-row')
                                .append($('<td>').text(s.facultyName))
                                .append($('<td>').text(s.departmentName))
                                .append($('<td>').text(s.specialtyName))
                                .append($('<td>').text(dformat))
                                .append($('<td>')
                                    .append($('<form>').attr('action', `/specialty/delete/${s.id}`).attr('method', 'POST')
                                        .append($('<input>').attr('type', 'hidden').attr('name', '_csrf').val(csrf))
                                        .append($('<button>').attr('type', 'submit').addClass('btn btn-danger').text(deleteButtonText.val())))));
                        });
                    }
                }
            );
    }
});
