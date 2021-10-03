<html>
    <head>
        <meta charset="UTF-8">
        <title>Monitor Service</title>
        <script type="application/javascript">
            function getMonitorServicesAndLoad() {
                fetch('http://localhost:9090/monitor-service/monitor/api/services?buster=' + new Date().getTime())
                    .then(response => response.json())
                    .then(monitorServiceList => {
                        const tableObj = document.getElementById('monitor-service-list');
                        monitorServiceList.forEach(service => {
                            const tableRow = document.createElement('tr');
                            const serviceNameTd = document.createElement('td');
                            serviceNameTd.setAttribute('align', 'center');
                            const serviceUrlTd = document.createElement('td');
                            serviceUrlTd.setAttribute('align', 'center');
                            const createdTd = document.createElement('td');
                            createdTd.setAttribute('align', 'center');
                            const updatedTd = document.createElement('td');
                            updatedTd.setAttribute('align', 'center');
                            const serviceStatusTd = document.createElement('td');
                            serviceStatusTd.setAttribute('align', 'center');
                            const serviceUpdateTd = document.createElement('td');
                            serviceUpdateTd.setAttribute('align', 'center');
                            const serviceRemoveTd = document.createElement('td');
                            serviceRemoveTd.setAttribute('align', 'center');
                            const serviceUpdateButton = document.createElement('button');
                            const serviceRemoveButton = document.createElement('button');

                            //set data and on click for delete
                            serviceNameTd.innerHTML = service.serviceName;
                            serviceUrlTd.innerHTML = '<a href='+ service.serviceUrl + ' target=new>' + service.serviceUrl + '</a>';
                            createdTd.innerHTML = service.created;
                            updatedTd.innerHTML = service.updated;
                            serviceStatusTd.innerHTML = service.status;

                            //add onclicks
                            serviceUpdateButton.innerHTML = 'Update';
                            serviceUpdateButton.onclick = () => {showUpdateForm(service.serviceName, service.serviceUrl)};
                            serviceUpdateTd.appendChild(serviceUpdateButton);
                            serviceRemoveButton.innerHTML = 'Remove';
                            serviceRemoveButton.onclick = () => {removeService(service.serviceName)};
                            serviceRemoveTd.appendChild(serviceRemoveButton);

                            // append nodes
                            tableRow.appendChild(serviceNameTd);
                            tableRow.appendChild(serviceUrlTd);
                            tableRow.appendChild(createdTd);
                            tableRow.appendChild(updatedTd);
                            tableRow.appendChild(serviceStatusTd);
                            tableRow.appendChild(serviceUpdateTd);
                            tableRow.appendChild(serviceRemoveTd);
                            tableObj.appendChild(tableRow);
                        });
                    });
            }

            function addServiceFromForm() {
                const serviceName = document.forms.addServiceForm.serviceName.value;
                const serviceUrl = document.forms.addServiceForm.serviceUrl.value;
                addOrUpdateService(serviceName, serviceUrl, false);
            }

            function showUpdateForm(serviceName, serviceUrl) {
                document.forms.updateServiceForm.editServiceName.value = serviceName;
                document.forms.updateServiceForm.editServiceUrl.value = serviceUrl;
                document.forms.updateServiceForm.style.display = '';
            }

            function updateServiceFromForm() {
                const serviceName = document.forms.updateServiceForm.editServiceName.value;
                const serviceUrl = document.forms.updateServiceForm.editServiceUrl.value;
                addOrUpdateService(serviceName, serviceUrl, true);
            }

            function addOrUpdateService(serviceName, serviceUrl, isUpdate) {
                let url = 'http://localhost:9090/monitor-service/monitor/api';
                url += isUpdate ? '/update' : '/save';
                fetch(url, {
                    method: 'post',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({serviceName: serviceName, serviceUrl: serviceUrl})
                }).then(response => response.json())
                    .then(serviceResponse => {
                        if (serviceResponse && !serviceResponse.success) {
                            alert(serviceResponse.message);
                        } else {
                            alert(serviceResponse.message);
                            window.location.reload();
                        }
                    });
            }

            function removeService(serviceName) {
                fetch('http://localhost:9090/monitor-service/monitor/api/remove', {
                    method: 'post',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({serviceName: serviceName})
                }).then(response => response.json())
                .then(serviceResponse => {
                    if (serviceResponse && !serviceResponse.success) {
                        alert(serviceResponse.message);
                    } else {
                        alert(serviceResponse.message);
                        window.location.reload();
                    }
                });
            }
        </script>
        <style type="text/css">
            table {
                border-collapse:separate;
                border:solid black 1px;
                border-radius:6px;
                -moz-border-radius:6px;
            }

            td, th {
                border-left:solid black 1px;
                border-top:solid black 1px;
            }

            th {
                border-top: none;
            }

            td:first-child, th:first-child {
                border-left: none;
            }
        </style>
    </head>
    <body>

        <h3>Add New Service</h3>
        <form id="addServiceForm" name="addService" action="post">
            <div>
                Service Name: <input type="text" id="serviceName" name="serviceName" value="">
            </div>
            <p/>
            <div>
                Service Url: <input type="text" id="serviceUrl" name="serviceUrl" value="">
            </div>
            <p/>
            <input type="button" name="Add" value="Add" onclick="addServiceFromForm()"/>
        </form>

        <H3>Monitor Service List</H3>
        <table id="monitor-service-list" class="display" style="width:80%" border="0" style="border: aliceblue">
            <thead>
            <tr>
                <th>Service Name</th>
                <th>Service Url</th>
                <th>Created</th>
                <th>Updated</th>
                <th>Status</th>
                <th>Update Service</th>
                <th>Remove Service</th>
            </tr>
            </thead>
        </table>

        <br>

        <form id="updateServiceForm" name="updateServiceForm" action="post" style="display:none">
            <h3>Update Service</h3>
            <div>
                Service Name: <input type="text" id="editServiceName" name="editServiceName" value="" disabled>
            </div>
            <p/>
            <div>
                Service Url: <input type="text" id="editServiceUrl" name="editServiceUrl" value="">
            </div>
            <p/>

            <input type="button" name="Update" value="Update" onclick="updateServiceFromForm()"/>
        </form>

        <script>
            getMonitorServicesAndLoad();
            window.setInterval(() => {
                window.location.reload();
            }, 10000);
        </script>
    </body>
</html>