<template>
  <v-card :flat="$store.getters.isMobile" class="table pb-4">
    <v-card-title>Participantes no DEI às Quartas</v-card-title>
    <v-card-text>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Pesquisar"
        single-line
        hide-details
      ></v-text-field>
      <v-data-table
        :headers="headers"
        :items="attendees"
        :search="search"
        :loading="loading"
        single-select
        locale="pt-PT"
        no-data-text="Não existem participantes registados"
        no-results-text="Nenhum participante corresponde aos critérios indicados"
        sort-by="name"
      >
        <template v-slot:[`item.type`]="{ item }">
          <v-chip
            v-if="item.type === 'Professor'"
            color="purple"
            text-color="white"
          >
            Professor
          </v-chip>
          <v-chip v-else color="green" text-color="white"> Bolseiro </v-chip>
        </template>
        <template v-slot:[`item.actions`]="{ item }">
          <v-btn
            color="primary"
            :to="`/attendees/${item.id}`"
          >
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
          <v-btn
            color="error"
            @click="removeAttendee(item)"
          >
            <v-icon>mdi-delete</v-icon>
          </v-btn>
        </template>
      </v-data-table>
        <!-- add attendee button-->
      <v-btn
      v-if="!$store.getters.isMobile"
      color="primary"
      class="mx-auto"
      :to="`/attendees/add-attendee`"
      >
      <v-icon left>mdi-plus</v-icon> Adicionar Participante
      </v-btn>
    </v-card-text>
  </v-card>
</template>

<script lang="ts">
import AttendeeDto from '@/models/deiwed/AttendeeDto';
import RemoteServices from '@/services/RemoteServices';
import { Component, Vue } from 'vue-property-decorator';
import { DataTableHeader } from 'vuetify';

@Component
export default class AttendeesView extends Vue {
  attendees: AttendeeDto[] = [];
  headers: DataTableHeader[] = [
    { text: 'ID', value: 'id', sortable: true, filterable: true },
    { text: 'Nome', value: 'name', sortable: true, filterable: true },
    { text: 'IST ID', value: 'istId', sortable: true, filterable: true },
    { text: 'Tipo', value: 'type', sortable: true, filterable: false },
    // TODO: maybe add another column with possible actions? (edit / delete)
    { text: 'Ações', value: 'actions', sortable: false, filterable: false },
  ];

  search = '';
  loading = true;

  async mounted() {
    await this.$store.dispatch('loading');
    try {
      this.attendees = await RemoteServices.getAttendees();
      this.loading = false;
    } catch (error) {
      this.$store.dispatch('error', error);
    }
    await this.$store.dispatch('clearLoading');
  }

  // remove attendee
  async removeAttendee(attendee: AttendeeDto) {
    await this.$store.dispatch('loading');
    try {
      await RemoteServices.removeAttendee(attendee.id);
      this.attendees = this.attendees.filter((a) => a.id !== attendee.id); // remove from list instead of reloading
    } catch (error) {
      this.$store.dispatch('error', error);
    }
    await this.$store.dispatch('clearLoading');
  }
}
</script>
