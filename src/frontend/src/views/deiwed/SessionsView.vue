<!-- create sessions template to view all sessions available in a fast access table with date, theme and speaker -->
<template>
  <v-card :flat="$store.getters.isMobile" class="table pb-4">
    <v-card-title>Sessões do DEI às Quartas</v-card-title>
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
        :items="sessions"
        :search="search"
        :loading="loading"
        single-select
        locale="pt-PT"
        no-data-text="Não existem sessões registadas"
        no-results-text="Nenhuma sessão corresponde aos critérios indicados"
        sort-by="id"
      >
        <template v-slot:[`item.actions`]="{ item }">
          <v-btn
            color="primary"
            :to="`/sessions/edit-session${item.id}`"
          >
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
          <v-btn
            color="error"
            @click="removeSession(item)"
          >
            <v-icon>mdi-delete</v-icon>
          </v-btn>
        </template>
        <template v-slot:[`item.details`]="{ item }">
        <!-- small button to see session details-->
        <v-btn
          x-small
          color="primary"
          :to="`/sessions/${item.id}`"
        >
          <v-icon>mdi-eye</v-icon>
        </v-btn>
      </template>
      </v-data-table>
      <v-btn
      v-if="!$store.getters.isMobile"
      color="primary"
      class="mx-auto"
      :to="`/sessions/add-session`"
      >
      <v-icon left>mdi-plus</v-icon> Adicionar Sessão
      </v-btn>
    </v-card-text>
  </v-card>
</template>

<script lang="ts">
import SessionDto from '@/models/deiwed/SessionDto';
import RemoteServices from '@/services/RemoteServices';
import { Component, Vue } from 'vue-property-decorator';
import { DataTableHeader } from 'vuetify';

@Component
export default class SessionsView extends Vue {
  sessions: SessionDto[] = [];
  headers: DataTableHeader[] = [
    { text: 'ID', value: 'id', sortable: true, filterable: true },
    { text: 'Data', value: 'date', sortable: true, filterable: true },
    { text: 'Orador', value: 'speaker', sortable: true, filterable: true },
    { text: 'Tema', value: 'theme', sortable: true, filterable: true },
    { text: 'Ações', value: 'actions', sortable: false, filterable: false },
    { text: 'Ver Detalhes', value: 'details', sortable: false, filterable: false },

];
  search = '';
  loading = true;

  async mounted() {
    await this.$store.dispatch('loading');
    try {
      this.sessions = await RemoteServices.getSessions();
      this.loading = false;
    } catch (error) {
      this.$store.dispatch('error', error);
    }
    await this.$store.dispatch('clearLoading');
  }

    // remove session
    async removeSession(session: SessionDto) {
    await this.$store.dispatch('loading');
    try {
      RemoteServices.removeOrder(session.date);
      await RemoteServices.removeAllAttendeesFromSession(session.id);
      await RemoteServices.removeSession(session.id);
      this.sessions = this.sessions.filter((a) => a.id !== session.id); // remove from list instead of reloading
    } catch (error) {
      this.$store.dispatch('error', error);
    }
    await this.$store.dispatch('clearLoading');
  }
}
</script>