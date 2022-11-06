<!-- see all sessions an attendee is registered -->
<template>
    <v-card :flat="$store.getters.isMobile" class="table pb-4">
      <v-card-title>Sessões a que {{ attendee.name}} está inscrito</v-card-title>
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
          :items="sessions_of_attendee"
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
        <v-dialog v-model="dialog" max-width="500px">
        <template v-slot:activator="{ on }">
            <v-btn color="green" dark class="mb-2" v-on="on">
                <v-icon left>mdi-plus</v-icon> Adicionar Sessão
            </v-btn>
        </template>
        <v-card>
            <v-card-title>
                <span class="headline">Adicionar Sessão</span>
            </v-card-title>
            <v-card-text>
                <v-container>
                    <v-row>
                        <v-col cols="12">
                            <v-autocomplete
                                v-model="session"
                                :items="sessions"
                                item-text="theme"
                                item-value="id"
                                label="Sessão"
                                return-object
                                outlined
                                dense
                                clearable
                                :loading="loading"
                                :no-data-text="noDataText"
                                :no-filter="!search"
                                :loading-text="loadingText"
                                :error-messages="errors.session"
                            ></v-autocomplete>
                        </v-col>
                    </v-row>
                </v-container>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="close">Cancelar</v-btn>
                <v-btn color="blue darken-1" text @click="save">Salvar</v-btn>
            </v-card-actions>
        </v-card>
        </v-dialog>
      </v-card-text>
    <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text :to="`/attendees`">Todas os Participantes</v-btn>
    </v-card-actions>
    </v-card>
</template>

<script lang="ts">
import SessionDto from '@/models/deiwed/SessionDto';
import AttendeeDto from '@/models/deiwed/AttendeeDto';
import SessionAttendeesDto from '@/models/deiwed/SessionAttendeesDto';
import RemoteServices from '@/services/RemoteServices';
import { Component, Vue } from 'vue-property-decorator';
import { DataTableHeader } from 'vuetify';

@Component
export default class SessionsView extends Vue {
  attendee: AttendeeDto = new AttendeeDto();
  session: SessionDto = new SessionDto();
  sessions_id: SessionAttendeesDto[] = [];
  sessions_of_attendee: SessionDto[] = [];
  sessions: SessionDto[] = [];
  dialog = false;
  search = "";
  loading = false;
  loadingText = "A procurar...";
  noDataText = "Não foram encontradas sessões";
  errors: any = {};

  headers: DataTableHeader[] = [
    { text: 'ID', value: 'id', sortable: true, filterable: true },
    { text: 'Data', value: 'date', sortable: true, filterable: true },
    { text: 'Orador', value: 'speaker', sortable: true, filterable: true },
    { text: 'Tema', value: 'theme', sortable: true, filterable: true },
    { text: 'Ações', value: 'actions', sortable: false, filterable: false },
    { text: 'Ver detalhes', value: 'details', sortable: false, filterable: false },

];

  async mounted() {
    this.loading = true;
    this.attendee = await RemoteServices.getAttendee(parseInt(this.$route.params.id));
    this.sessions_id = await RemoteServices.getAllSessionsOfAttendee(this.attendee.id);
    for (let i = 0; i < this.sessions_id.length; i++) {
        this.sessions_of_attendee.push(await RemoteServices.getSession(this.sessions_id[i].sessionId));
    }
    await this.getSessions();
    this.loading = false;
  }

  async getSessions() {
        this.loading = true;
        this.sessions = await RemoteServices.getSessions();
        // remove attendees that are already in the session
        for (let i = 0; i < this.sessions_of_attendee.length; i++) {
            this.sessions = this.sessions.filter((a) => a.id !== this.sessions_of_attendee[i].id);
        }
        this.loading = false;
    }

    // remove session
    async removeSession(session: SessionDto) {
        this.loading = true;
        await RemoteServices.removeAttendeeFromSession(session.id, this.attendee.id);
        this.loading = false;
        // refresh page to garantee that the attendee is removed from table and from attendees_of_session
        this.$router.go(0);
    }

    async save(): Promise<void> {
        // cannot be empty
        this.errors = {};
        if (this.session.id === 0) {
            this.errors.session = ["A sessão não pode estar vazia"];
            return;
        }
        const sessionAttendee = new SessionAttendeesDto();
        sessionAttendee.attendeeId = this.attendee.id;
        sessionAttendee.sessionId = this.session.id;
        await RemoteServices.addAttendeeToSession(sessionAttendee);
        this.close();
        // refresh page
        this.$router.go(0);
    }

    close() {
        this.dialog = false;
        this.attendee = new AttendeeDto();
    }
}
</script>