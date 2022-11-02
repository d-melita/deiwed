import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('@/views/HomeView.vue'),
    },
    {
      path: '/attendees',
      name: 'attendees',
      component: () => import('@/views/deiwed/AttendeesView.vue'),
    },
    {
      path: '/dishes',
      name: 'dishes',
      component: () => import('@/views/deiwed/DishesView.vue'),
    },
    {
      path: '/sessions',
      name: 'sessions',
      component: () => import('@/views/deiwed/SessionsView.vue'),
    },
    {
      path: '/attendees/add-attendee',
      name: 'addattendee',
      component: () => import('@/views/deiwed/AddAttendeeView.vue'),
    },
    {
      path: '/attendees/:id',
      name: 'editattendee',
      component: () => import('@/views/deiwed/EditAttendeeView.vue'),
    },
    {
      path: '/sessions/add-session',
      name: 'addsession',
      component: () => import('@/views/deiwed/AddSessionView.vue'),
    },
    {
      path: '/sessions/edit-session:id',
      name: 'editsession',
      component: () => import('@/views/deiwed/EditSessionView.vue'),
    },
    {
      path: '/sessions/:id',
      name: 'session',
      component: () => import('@/views/deiwed/SessionView.vue'),
    }
  ],
});

export default router;
