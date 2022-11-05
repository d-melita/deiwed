<!-- edit attendee page - edit attendee with name, istId, type -->
<template>
    <v-card>
        <v-card-title>
        <h1 class="display-1">Editar Sessão</h1>
        </v-card-title>
        <v-card-text>
        <v-form>
            <v-card-title class="text-center">
                <h3>Data Atual: {{ current_session.date }}</h3>
            </v-card-title>
            <v-text-field
            v-model="date"
            label="Data (aaaa-mm-dd)"
            ></v-text-field>
            <v-card-title class="text-center">
                <h3>Tema Atual: {{ current_session.theme }}</h3>
            </v-card-title>
            <v-text-field
            v-model="theme"
            label="Tema"
            ></v-text-field>
            <v-card-title class="text-center">
                <h3>Orador Atual: {{ current_session.speaker }}</h3>
            </v-card-title>
            <v-text-field
            v-model="speaker"
            label="Orador"
            ></v-text-field>
        </v-form>
        <!-- button bottom right -->
        <v-btn color="primary" class="" @click="editSession">
            <v-icon left>mdi-plus</v-icon> Editar Sessão
        </v-btn>
        <!-- cancel button bottom left -->
        <v-btn color="error" class="" @click="goTo('/sessions')">
            <v-icon left>mdi-close</v-icon> Cancelar
        </v-btn>
        </v-card-text>
    </v-card>
</template>

<script lang="ts">
import SessionDto from '@/models/deiwed/SessionDto';
import RemoteServices from '@/services/RemoteServices';
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class editSession extends Vue {
    id: number = 0;
    date: string = '';
    theme: string = '';
    speaker: string = '';
    image: string = '';

    current_session: SessionDto = new SessionDto();

    async mounted() {
        this.current_session = await RemoteServices.getSession(parseInt(this.$route.params.id));
    }

  async editSession() {
    const session: SessionDto = {
        id: parseInt(this.$router.currentRoute.params.id),
        date: this.date,
        theme: this.theme,
        speaker: this.speaker,
        image: this.image,
    };
    if (session.date === '') {
        session.date = this.current_session.date;
    }
    if (session.theme === '') {
        session.theme = this.current_session.theme;
    }
    if (session.speaker === '') {
        session.speaker = this.current_session.speaker;
    }
    if (session.image === '') {
        session.image = this.current_session.image;
    }
    await RemoteServices.editSession(session);
    this.$router.push('/sessions');
  }

  goTo(path: string): void {
    if (this.$route.path == path) {
      this.$router.go(0);
    } else {
      this.$router.push(path);
    }
  }
}
</script>