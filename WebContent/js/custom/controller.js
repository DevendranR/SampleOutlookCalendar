var URI = "http://localhost:6453/Calendar/api/";

var fiveyVentures = angular.module("fiveyVentures", ['ngRoute']);

fiveyVentures.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'index.html',
		controller : 'indexController'
	}).otherwise({
		redirectTo : '/'
	});
} ]);

fiveyVentures.config(function($httpProvider) {
	$httpProvider.defaults.headers.post['Content-Type'] = "application/json; charset=UTF-8";
	$httpProvider.defaults.headers.post['Data-Type'] = "json";
});

fiveyVentures.controller(
		'indexController', 
		function ($scope,$http,$compile) {
			$scope.chooseYear= []; 
			$scope.chooseMonth=[{"month":"January","index":0},
			                    {"month":"February","index":1},
			                    {"month":"March","index":2},
			                    {"month":"April","index":3},
			                    {"month":"May","index":4},
			                    {"month":"June","index":5},
			                    {"month":"July","index":6},
			                    {"month":"August","index":7},
			                    {"month":"September","index":8},
			                    {"month":"October","index":9},
			                    {"month":"November","index":10},
			                    {"month":"December","index":11}]
			$scope.selectedYear="";
			$scope.selectedMonth={};
			$scope.selectedTeam="Dev";
			$scope.scheduledName=[];
			$scope.teams=[];
			$scope.teamMembers=[];
			$scope.message=""
			/*var div = angular.element("butID");
		    div.bind("click", $scope.scheduleShift);*/
			$scope.scheduleShift = function(teamMember,date) {
				alert("hikkkkkk")
				var month = $scope.selectedMonth.index+1;
				var data = {"employeeName":teamMember,"assignedDate":date+"-"+month+"-"+$scope.selectedYear,"employeeTeam":$scope.selectedTeam};
				
				try {
					$scope.message="";
					var responsePromise = $http.post(URI + "ScheduleAPI",data);
					
				} catch (err) {
					
				}
				responsePromise
						.success(function(dataFromServer, status,headers, config) {
							
							$scope.message = "Shift for "+teamMember+" from "+$scope.selectedTeam+" has been scheduled on"+date+"-"+month+"-"+$scope.selectedYear;
							$scope.scheduledNames[date]=dataFromServer;
							var column = document.getElementById('column'+date);
							column.innerHTML = populateCellContent(date);
		            $compile(column)($scope);
						});
				responsePromise.error(function(data, status,
						headers, config) {
					$scope.message = data;
				});
				
				
		   

		}

			$scope.populateTeams = function(){
				try {
					var responsePromise = $http.get(URI + "ScheduleAPI");
					
				} catch (err) {
					alert(err)
				}
				responsePromise
						.success(function(dataFromServer, status,headers, config) {
							$scope.selectedTeam = dataFromServer[0];
							$scope.teams = dataFromServer;
						});
				responsePromise.error(function(data, status,
						headers, config) {
					alert(data);
				});
			}
			
			$scope.populateTeamMembers = function(team){
				try {
					var responsePromise = $http.put(URI + "ScheduleAPI",team);
					
				} catch (err) {
					alert(err)
				}
				responsePromise
						.success(function(dataFromServer, status,headers, config) {
							$scope.teamMembers = dataFromServer;
						});
				responsePromise.error(function(data, status,
						headers, config) {
					alert(data);
				});
			}
			$scope.fillYears = function(){
				var today = new Date();
			    var thisYear = today.getFullYear();
			    $scope.selectedYear = today.getFullYear();
			    for (i = 0; i < 5; i++) {
			    	$scope.chooseYear[i] = thisYear;
			    	thisYear = thisYear+1;
			    }
			    for(i=0;i<$scope.chooseMonth.length;i++){
			    	if(today.getMonth()==$scope.chooseMonth[i].index){
			    		$scope.selectedMonth = $scope.chooseMonth[i];
			    	}
			    }
			    
			}
			var theMonths = ["January","February","March","April","May","June","July","August",
			                 "September","October","November","December"]
			                 // return IE4+ or W3C DOM reference for an ID
	         function getObject(obj) {
	             var theObj
	             if (document.all) {
	                 if (typeof obj == "string") {
	                     return document.all(obj)
	                 } else {
	                     return obj.style
	                 }
	             }
	             if (document.getElementById) {
	                 if (typeof obj == "string") {
	                     return document.getElementById(obj)
	                 } else {
	                     return obj.style
	                 }
	             }
	             return null
	         }
			$scope.populateTable = function(form,teams){
				$scope.scheduledName=[];
				
				if(teams!=""&&teams!=undefined){
					$scope.selectedTeam=teams;
				}
				var firstDay = getFirstDay($scope.selectedYear, $scope.selectedMonth)
			    var howMany = getMonthLen($scope.selectedYear, $scope.selectedMonth)
			    
			    getObject("tableHeader").innerHTML = theMonths[$scope.selectedMonth.index] + " " + $scope.selectedYear

			    var dayCounter = 1
			    var TBody = getObject("tableBody")
			    while (TBody.rows.length > 0) {
			        TBody.deleteRow(0)
			    }
			    var newR, newC
			    var done=false
			    while (!done) {
			        newR = TBody.insertRow(TBody.rows.length)
			        for (var i = 0; i < 7; i++) {
			            newC = newR.insertCell(newR.cells.length)
			            if (TBody.rows.length == 1 && i < firstDay) {
			                newC.innerHTML = ""    
			                continue;
			            }
			            if (dayCounter == howMany) {
			                done = true
			            }
			            var newDate =dayCounter++;
			           
			            if((dayCounter <= howMany)){
				            var month = $scope.selectedMonth.index+1;
				            var data = {"assignedDate":newDate+"-"+month+"-"+$scope.selectedYear,"employeeTeam":$scope.selectedTeam};
							$scope.scheduledNames=[];
							
				            try {
								var responsePromise = $http.put(URI + "ScheduleAPI/teamMembers",data);
								
							} catch (err) {
								alert(err)
							}
							responsePromise
									.success(function(dataFromServer, status,headers, config) {
										var date=config.data.assignedDate.split("-")[0];
										$scope.scheduledNames[date]=dataFromServer;
										
									});
				            
				            newC.innerHTML = populateCellContent(newDate); 
				            $compile(newC)($scope);
			            }
			        }
			       
			    }
			}
			function getFirstDay(theYear, theMonth){
			    var firstDate = new Date(theYear,theMonth.index,1)
			    return firstDate.getDay()
			}
			function getMonthLen(theYear, theMonth) {
			    var oneDay = 1000 * 60 * 60 * 24
			    var thisMonth = new Date(theYear, theMonth.index, 1)
			    var nextMonth = new Date(theYear, theMonth.index + 1, 1)
			    var len = Math.ceil((nextMonth.getTime() - 
			        thisMonth.getTime())/oneDay)
			    return len
			}
			$scope.removeFromSchedule = function(employee){
				
				var month = $scope.selectedMonth.index+1;
				try {
					var data = angular.toJson(employee.scheduleId);
					var responsePromise = $http.delete(URI + "ScheduleAPI/"+data);
					
				} catch (err) {
					alert(err)
				}
				responsePromise
						.success(function(dataFromServer, status,headers, config) {
							var date=employee.assignedDate.split("-")[0].replace(/^0+/, '');
							$scope.message = "Shift for "+employee.employeeName+" from "+$scope.selectedTeam+" has been removed on "+date+"-"+month+"-"+$scope.selectedYear;
							$scope.scheduledNames[date]=dataFromServer;
							var column = document.getElementById('column'+date);
							column.innerHTML = populateCellContent(date);
		            $compile(column)($scope);
						});
				responsePromise.error(function(data, status,
						headers, config) {
					alert(data);
				});
			}
			
			function populateCellContent(date){
				return "<div id=column"+date+">"+date +"<div class='nameAlign'>" +
				"<div ng-repeat='eName in scheduledNames["+date+"]'>" +
				"<div class='assignedNames form-control'>{{eName.employeeName}}" +
				"<i class='fa fa-times' aria-hidden='true' ng-click='removeFromSchedule(eName)'></i></div></div></div>" +
		"<select class='nameSelect form-control' ng-options='x for x in teamMembers' ng-model='scheduledName["+date+"]' " +
				"ng-change='scheduleShift(scheduledName["+date+"],"+date+")'></select></div>";
			}
			
			
		});


	
