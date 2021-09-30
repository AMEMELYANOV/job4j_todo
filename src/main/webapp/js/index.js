$(function () {
    loadTasks()
});

function loadTasks() {
    $.ajax({
        type: 'GET',
        url: '/todo/index.do',
        data: {
            completed: $("#completed")[0].checked
        },
        dataType: 'json'
    }).done(function (data) {
        $("#table_body").empty();
        for (let task of data) {
            const options = {year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: "numeric"};
            let created = new Date(task.created).toLocaleDateString("ru", options);
            let checked = task.done ? "checked" : "";
            let row = `
                   <tr>
                      <td>${task.describe}</td>
                      <td>${created}</td>
                      <td>
                         <div class="change_state_div" id="change_state_${task.id}">
                            <input hidden name="id" value="${task.id}">
                            <input type="checkbox" name="state" ${checked}>
                        </div>
                      </td>
                    </tr>`;
            $("#table_body").append(row);
        }
    }).fail(function (err) {
        console.log(err);
    });
}

function validate() {
    return $('#newDesc').val() !== '';
}

function addNewTask() {
    if (validate()) {
        $.ajax({
            type: 'POST',
            url: '/todo/index.do',
            data: JSON.stringify({
                describe: $('#newDesc').val()
            }),
            dataType: 'json'
        }).done(function () {
            $("#newDesc").val("");
            loadTasks()
        }).fail(function (err) {
            console.log(err);
        });
    }
}

$(document).on("change", "#completed", function () {
        loadTasks();
    }
);

$(document).on("change", ".change_state_div", function () {
        let id = $($(this).children()[0]).attr("value");
        let done = $($(this).children()[1])[0].checked;
        $.ajax({
            type: 'POST',
            url: '/todo/tasked.do',
            data: {
                id: id,
                done: done
            }
        }).done(function () {
            loadTasks();
        }).fail(function (err) {
            console.log(err)
        })
    }
);