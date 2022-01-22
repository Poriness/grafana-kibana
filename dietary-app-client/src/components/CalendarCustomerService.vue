<script>
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import timeGridPlugin from '@fullcalendar/timegrid'
import interactionPlugin from '@fullcalendar/interaction'
import DropdownList from './DropdownList'
import AppointmentsService from './../services/appointments.service.js';
import UserService from "../services/user.service";


export default {

  components: {
    FullCalendar, // make the <FullCalendar> tag available
    DropdownList,
  },

  data: function () {
    return {
      calendarOptions: {
        plugins: [
          dayGridPlugin,
          timeGridPlugin,
          interactionPlugin, // needed for dateClick
        ],
        headerToolbar: {
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,timeGridWeek,timeGridDay',
        },
        initialView: 'timeGridWeek',
        firstDay: 1,
        events: function () {
          return AppointmentsService.getAllAppointments()
        },
        editable: true,
        selectable: true,
        selectMirror: true,
        dayMaxEvents: true,
        weekends: true,
        select: this.handleDateSelect,
        eventClick: this.handleEventClick,
        eventsSet: this.handleEvents,
        eventAdd: this.addEventsToDatabase,
        eventChange: this.updateEventInDatabase,
        eventRemove: this.removeEventInDatabase

      },
      currentEvents: [],
      dietitianDropdownSelection: {},
      clientDropdownSelection: {},
      clientUrl: "",
      clearClient: false,
      showDropdowns: false,
      clientDropdownDisabled: true,
      allDietitianUrl: UserService.getAllDietitianUrl(),
    }
  },

  methods: {

    handleWeekendsToggle() {
      this.calendarOptions.weekends = !this.calendarOptions.weekends // update a property
    },

    handleDateSelect(selectInfo) {
      let oneDay = new Date().getTime() + (24 * 60 * 60 * 1000);
      let calendarApi = selectInfo.view.calendar
      if (oneDay > selectInfo.start) {
        confirm(`It is too late to add event on this date`);
        calendarApi.unselect() // clear date selection
      } else if (Object.keys(this.dietitianDropdownSelection).length === 0
          || Object.keys(this.clientDropdownSelection).length === 0) {
        confirm(`First select dietitian and client`);
        calendarApi.unselect() // clear date selection
      } else {
        let title = prompt('Please enter a new title for your event')

        calendarApi.unselect() // clear date selection
        if (title) {
          calendarApi.addEvent(
              {
                id: -1,
                title,
                start: selectInfo.startStr,
                end: selectInfo.endStr,
                allDay: selectInfo.allDay,
                dietitianId: this.dietitianDropdownSelection.userId,
                clientId: this.clientDropdownSelection.userId,
              },
              true
          )
        }
      }
    },

    handleEventClick(clickInfo) {
      let oneDay = new Date().getTime() + (24 * 60 * 60 * 1000);
      if (oneDay > clickInfo.event.start) {
        confirm(`It is too late to remove this event '${clickInfo.event.title}'`);
      } else {
        if (confirm(`Are you sure you want to delete the event '${clickInfo.event.title}'`)) {
          clickInfo.event.remove()
        }
      }
    },

    handleEvents(events) {
      this.currentEvents = events
    },

    addEventsToDatabase(addInfo) {
      let lastEvent = {
        clientId: addInfo.event.extendedProps.clientId,
        dietitianId: addInfo.event.extendedProps.dietitianId,
        status: 'ACTIVE',
        title: addInfo.event.title,
        start: addInfo.event.start,
        end: addInfo.event.end,
        allDay: addInfo.event.allDay,
      }
      AppointmentsService.createAppointment(lastEvent);
    },

    updateEventInDatabase(addInfo) {
      let updatedEvent = {
        id: addInfo.event.id,
        clientId: addInfo.event.extendedProps.clientId,
        dietitianId: addInfo.event.extendedProps.dietitianId,
        status: addInfo.event.extendedProps.status,
        title: addInfo.event.title,
        start: addInfo.event.start,
        end: addInfo.event.end,
        allDay: addInfo.event.allDay,
      }
      AppointmentsService.updateAppointment(updatedEvent);
      console.warn(updatedEvent)
    },

    removeEventInDatabase(info) {
      let removedEventId = info.event.id;
      AppointmentsService.removeAppointment(removedEventId);
      console.warn(removedEventId)
    },

    handleChooseD(user) {
      this.dietitianDropdownSelection = user;
      this.clientUrl = UserService.getAllClientsForDietitianUrl(this.dietitianDropdownSelection.username)
      this.clearClient = true
      this.clientDropdownDisabled = false
      this.calendarOptions.events =  function () {
        let id = user.dietitianId;
        return AppointmentsService.getAllAppointmentsForDietitian(id)
      }
    },

    handleUnselectD() {
      this.dietitianDropdownSelection = {};
      this.calendarOptions.events = function () {
        return AppointmentsService.getAllAppointments()
      };
      this.clientUrl = ""
      this.handleUnselectClientDropdown()
      this.clearClient = true
      this.clientDropdownDisabled = true
    },

    handleChooseClientDropdown(user) {
      this.clientDropdownSelection = user;
      this.clearClient = false
    },

    handleUnselectClientDropdown() {
      this.clientDropdownSelection = {};
      this.clearClient = true
    }
  },


}
</script>

<template>
  <div class="demo-app">
    <div class="demo-app-sidebar">
      <div class="demo-app-sidebar-section">
        <h2>Instructions</h2>
        <ul>
          <li>Select dates and you will be prompted to create a new event</li>
          <li>Drag, drop, and resize events</li>
          <li>Click an event to delete it</li>
        </ul>
      </div>
      <div class="demo-app-sidebar-section">
        <label>
          <input
              type="checkbox"
              :checked="calendarOptions.weekends"
              @change="handleWeekendsToggle"
          />
          toggle weekends
        </label>
      </div>
      <div class="demo-app-sidebar-section">
        <div class="form-group">
          <h2>Choose Dietitian</h2>
          <DropdownList :url="this.allDietitianUrl"
                        placeholder="Dietitian"
                        :disable=false
                        @on-item-selected="handleChooseD($event)"
                        @on-item-reset="handleUnselectD"/>
        </div>
      </div>
      <div class="demo-app-sidebar-section">
        <div class="form-group">
          <h2>Choose Client</h2>
          <DropdownList :url=this.clientUrl
                        placeholder="Client"
                        :clear=this.clearClient
                        :show-dropdowns="this.showDropdowns"
                        :disable=this.clientDropdownDisabled
                        @on-item-selected="handleChooseClientDropdown($event)"
                        @on-item-reset="handleUnselectClientDropdown"/>
        </div>
      </div>
    </div>
    <div class="demo-app-main">
      <FullCalendar class="demo-app-calendar" :options="calendarOptions"></FullCalendar>
    </div>
  </div>
</template>

<style lang='css'>
h2 {
  margin: 0;
  font-size: 16px;
}

ul {
  margin: 0;
  padding: 0 0 0 1.5em;
}

li {
  margin: 1.5em 0;
  padding: 0;
}

b {
  /* used for event dates/times */
  margin-right: 3px;
}

.demo-app {
  display: flex;
  min-height: 100%;
  font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
  font-size: 14px;
}

.demo-app-sidebar {
  width: 300px;
  line-height: 1.5;
  background: #eaf9ff;
  border-right: 1px solid #d3e2e8;
}

.demo-app-sidebar-section {
  padding: 2em;
}

.demo-app-main {
  flex-grow: 1;
  padding: 3em;
}

.fc {
  /* the calendar root */
  max-width: 1200px;
  max-height: 780px;
  margin: 0 auto;
}

.fc .fc-button-primary {
  color: #fff;
  background-color: #78c2ad;
  border-color: #78c2ad;
}

.fc .fc-button-primary:not(:disabled).fc-button-active {
  color: #fff;
  background-color: #569487;
  border-color: #53a28d;
}

.fc-v-event {
  display: block;
  border: 1px solid #6cc3d5;
  background-color: #6cc3d5;
}

.fc .fc-button-primary:disabled {
  color: #fff;
  background-color: #79d7c1;
  border-color: #6bd0ba;
}

.fc .fc-button-primary:not(:disabled):active:focus, .fc .fc-button-primary:not(:disabled).fc-button-active:focus {
  box-shadow: 0 0 0 0;
}

</style>
